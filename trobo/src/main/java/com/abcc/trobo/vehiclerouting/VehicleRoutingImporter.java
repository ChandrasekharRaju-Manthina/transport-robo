/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abcc.trobo.vehiclerouting;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import org.optaplanner.examples.vehiclerouting.domain.Customer;
import org.optaplanner.examples.vehiclerouting.domain.Depot;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.location.AirLocation;
import org.optaplanner.examples.vehiclerouting.domain.location.DistanceType;
import org.optaplanner.examples.vehiclerouting.domain.location.Location;
import org.optaplanner.examples.vehiclerouting.domain.location.RoadLocation;
import org.optaplanner.examples.vehiclerouting.domain.location.segmented.HubSegmentLocation;
import org.optaplanner.examples.vehiclerouting.domain.location.segmented.RoadSegmentLocation;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedCustomer;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedDepot;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedVehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingDao;
import org.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingFileIO;

import com.abcc.trobo.domain.Address;
import com.abcc.trobo.domain.PickupPoint;
import com.abcc.trobo.domain.TripSheet;
import com.abcc.trobo.exception.ApplicationException;

public class VehicleRoutingImporter extends AbstractTxtSolutionImporter {

    public VehicleRoutingImporter() {
        super(new VehicleRoutingDao());
    }

    public VehicleRoutingImporter(boolean withoutDao) {
        super(withoutDao);
    }
    
    public TripSheet tripSheet;

    public TripSheet getTripSheet() {
		return tripSheet;
	}

	public void setTripSheet(TripSheet tripSheet) {
		this.tripSheet = tripSheet;
	}

	@Override
    public String getInputFileSuffix() {
        return VehicleRoutingFileIO.FILE_EXTENSION;
    }

    public VehicleRoutingInputBuilder createTxtInputBuilder() {
        return new VehicleRoutingInputBuilder();
    }
    
    @SuppressWarnings("rawtypes")
	public Solution readSolution(TripSheet tripSheet)  {
    	VehicleRoutingInputBuilder txtInputBuilder = createTxtInputBuilder();
    	  try {
    		txtInputBuilder.setTripSheet(tripSheet);
			Solution solution = txtInputBuilder.readSolution();
			return solution;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}    	  
    }

    public static class VehicleRoutingInputBuilder extends TxtInputBuilder {

        private VehicleRoutingSolution solution;
        
        private TripSheet tripSheet;

        public TripSheet getTripSheet() {
			return tripSheet;
		}

		public void setTripSheet(TripSheet tripSheet) {
			this.tripSheet = tripSheet;
		}

		private int customerListSize;
        private int vehicleListSize;
        private int capacity;
        private Map<Long, Location> locationMap;
        private List<Depot> depotList;
        
        @SuppressWarnings("unused")
		private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if (unit == 'K') {
              dist = dist * 1.609344;
            } else if (unit == 'N') {
              dist = dist * 0.8684;
              }
            BigDecimal bd = new BigDecimal(dist + "").round(new MathContext(7, RoundingMode.HALF_UP));
            return bd.doubleValue();
          }

          /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
          /*::  This function converts decimal degrees to radians             :*/
          /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
          private double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
          }

          /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
          /*::  This function converts radians to decimal degrees             :*/
          /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
          private double rad2deg(double rad) {
            return (rad * 180.0 / Math.PI);
          }

        
        @SuppressWarnings("rawtypes")
		public Solution readSolution() {
        	 solution = new TimeWindowedVehicleRoutingSolution();
        	 solution.setId(0L);
        	 solution.setName("Trip sheet");
        	 
        	 customerListSize = tripSheet.getPickUpPoints().size();
        	 solution.setDistanceType(DistanceType.ROAD_DISTANCE);
        	 solution.setDistanceUnitOfMeasurement("sec");
        	 
        	 locationMap = new LinkedHashMap<Long, Location>(customerListSize);
        	 List<Location> customerLocationList = new ArrayList<Location>(customerListSize);
        	 
        	 for(PickupPoint pickUpPoint: tripSheet.getPickUpPoints()) {
				Location location = new RoadLocation();
				location.setId(pickUpPoint.getAddress().getId());
				location.setLatitude(pickUpPoint.getAddress().getLatitude().doubleValue());
				location.setLongitude(pickUpPoint.getAddress().getLongitude().doubleValue());
				location.setName(pickUpPoint.getAddress().getAddressLine());
				customerLocationList.add(location);
				locationMap.put(location.getId(), location);
			}
        	 
//        	 Address[] addresses = new Address[customerListSize];
//        	 for(int i = 0; i < customerListSize; i++) {
//        		 Address address = new Address();
//        		 address.setLatitude(new BigDecimal(customerLocationList.get(i).getLatitude()+""));
//        		 address.setLongitude(new BigDecimal(customerLocationList.get(i).getLongitude()+""));
//        		 addresses[i] = address;        		 
//        	 }
//        	 GoogleMapsHelper helper = new GoogleMapsHelper();
//        	 DistanceMatrix matrix = helper.findDistanceMatrix(addresses, addresses);
//        	 System.out.println(matrix);
        	 
        	 Map<Long, Address> distanceMatrix = tripSheet.getDistanceMatrix();
        	 
        	 for (int i = 0; i < customerListSize; i++) {
                 RoadLocation location = (RoadLocation) customerLocationList.get(i);
                 Map<RoadLocation, Double> travelDistanceMap = new LinkedHashMap<RoadLocation, Double>(customerListSize);
                
                 for (int j = 0; j < customerListSize; j++) {
                    
                     
                     if (i == j) {
//                         if (travelDistance != 0.0 && !(customerLocationList.get(i).getLatitude() ==customerLocationList.get(j).getLatitude()
//                        		 && customerLocationList.get(i).getLongitude() == customerLocationList.get(j).getLongitude())) {
//                             throw new IllegalStateException("The travelDistance (" + travelDistance
//                                     + ") should be zero.");
//                         }
                     } else {
//                    	 double travelDistance = distance(customerLocationList.get(i).getLatitude(), 
//                        		 customerLocationList.get(i).getLongitude(), 
//                        		 customerLocationList.get(j).getLatitude(), customerLocationList.get(j).getLongitude(), 'K');
//                    	 double travelDistance = (double) matrix.rows[i].elements[j].distance.inMeters  / 1000;
                    	 double travelDistance;
                    	 if(distanceMatrix.get(customerLocationList.get(i).getId()) == null || 
                    			 distanceMatrix.get(customerLocationList.get(i).getId()).getTimeToEachAddrMap().get(customerLocationList.get(j).getId()) == null) {
                    		travelDistance =  distanceMatrix.get(customerLocationList.get(j).getId()).getTimeToEachAddrMap().get(customerLocationList.get(i).getId());
         				} else {
         					travelDistance = distanceMatrix.get(customerLocationList.get(i).getId()).getTimeToEachAddrMap().get(customerLocationList.get(j).getId());
         				}
                    	 
                    	 System.out.print(travelDistance + "-");
                         RoadLocation otherLocation = (RoadLocation) customerLocationList.get(j);
                         travelDistanceMap.put(otherLocation, travelDistance);
                     }
                 }
                 location.setTravelDistanceMap(travelDistanceMap);
             }
        	 
        	 solution.setLocationList(customerLocationList);
        	 
        	 List<Customer> customerList = new ArrayList<Customer>();
        	 for (int i = 0; i < customerListSize; i++) {
        		 for(int j=0;j < tripSheet.getPickUpPoints().get(i).getNumberOfEmployees();j++) {
        			 TimeWindowedCustomer customer = new TimeWindowedCustomer();
                     customer.setId(Long.parseLong(tripSheet.getPickUpPoints().get(i).getAddress().getId() + "" + j));
                     Location location = locationMap.get(tripSheet.getPickUpPoints().get(i).getAddress().getId());
                     if (location == null) {
                         throw new IllegalArgumentException("The customer with id (" + tripSheet.getPickUpPoints().get(i).getAddress().getId()
                                 + ") has no location (" + location + ").");
                     }
                     customer.setLocation(location);
                     customer.setDemand(1);
                     customer.setReadyTime(distanceMatrix.get(customerLocationList.get(i).getId()).getTimeToEachAddrMap().get(0L) * 1000L) ;
                     customer.setDueTime((distanceMatrix.get(customerLocationList.get(i).getId()).getTimeToEachAddrMap().get(0L) * 1000L)  + (600 * 1000L));
                     customer.setServiceDuration(60 * 1000L);
                     
                     customerList.add(customer);
                     
        		 }
             }
             solution.setCustomerList(customerList);
             
             depotList = new ArrayList<Depot>(customerListSize);
             TimeWindowedDepot depot = new TimeWindowedDepot();
             depot.setId(tripSheet.getPickUpPoints().get(0).getAddress().getId());
             Location location = locationMap.get(tripSheet.getPickUpPoints().get(0).getAddress().getId());
             depot.setLocation(location);
             depot.setReadyTime(0);
             depot.setDueTime(14400 * 1000L);
             
             depotList.add(depot);
             solution.setDepotList(depotList);
             
             
             vehicleListSize = tripSheet.getVehicles().size();
             
             List<Vehicle> vehicleList = new ArrayList<Vehicle>(vehicleListSize);
             for (int i = 0; i < vehicleListSize; i++) {
                 Vehicle vehicle = new Vehicle();
                 vehicle.setId(tripSheet.getVehicles().get(i).getId());
                 vehicle.setCapacity(tripSheet.getVehicles().get(i).getSeats());
                 vehicle.setDepot(depotList.get(0));
                 vehicleList.add(vehicle);
             }
             solution.setVehicleList(vehicleList);
             
        	 return solution;
        }

        @SuppressWarnings("rawtypes")
		public Solution readSolution1() throws IOException {
            String firstLine = readStringValue();
            if (firstLine.matches("\\s*NAME\\s*:.*")) {
                solution = new VehicleRoutingSolution();
                solution.setId(0L);
                solution.setName(removePrefixSuffixFromLine(firstLine, "\\s*NAME\\s*:", ""));
                readVrpWebFormat();
            } else if (splitBySpacesOrTabs(firstLine).length == 3) {
                solution = new VehicleRoutingSolution();
                solution.setId(0L);
                solution.setName(FilenameUtils.getBaseName(inputFile.getName()));
                String[] tokens = splitBySpacesOrTabs(firstLine, 3);
                customerListSize = Integer.parseInt(tokens[0]);
                vehicleListSize = Integer.parseInt(tokens[1]);
                capacity = Integer.parseInt(tokens[2]);
                readCourseraFormat();
            } else {
                solution = new TimeWindowedVehicleRoutingSolution();
                solution.setId(0L);
                solution.setName(firstLine);
                readTimeWindowedFormat();
            }
            BigInteger possibleSolutionSize
                    = factorial(customerListSize + vehicleListSize - 1).divide(factorial(vehicleListSize - 1));
            logger.info("VehicleRoutingSolution {} has {} depots, {} vehicles and {} customers with a search space of {}.",
                    getInputId(),
                    solution.getDepotList().size(),
                    solution.getVehicleList().size(),
                    solution.getCustomerList().size(),
                    getFlooredPossibleSolutionSize(possibleSolutionSize));
            return solution;
        }

        // ************************************************************************
        // CVRP normal format. See http://neo.lcc.uma.es/vrp/
        // ************************************************************************

        public void readVrpWebFormat() throws IOException {
            readVrpWebHeaders();
            readVrpWebLocationList();
            readVrpWebCustomerList();
            readVrpWebDepotList();
            createVrpWebVehicleList();
            readConstantLine("EOF");
        }

        private void readVrpWebHeaders() throws IOException {
            readUntilConstantLine("TYPE *: CVRP");
            customerListSize = readIntegerValue("DIMENSION *:");
            String edgeWeightType = readStringValue("EDGE_WEIGHT_TYPE *:");
            if (edgeWeightType.equalsIgnoreCase("EUC_2D")) {
                solution.setDistanceType(DistanceType.AIR_DISTANCE);
            } else if (edgeWeightType.equalsIgnoreCase("EXPLICIT")) {
                solution.setDistanceType(DistanceType.ROAD_DISTANCE);
                String edgeWeightFormat = readStringValue("EDGE_WEIGHT_FORMAT *:");
                if (!edgeWeightFormat.equalsIgnoreCase("FULL_MATRIX")) {
                    throw new IllegalArgumentException("The edgeWeightFormat (" + edgeWeightFormat + ") is not supported.");
                }
            } else if (edgeWeightType.equalsIgnoreCase("SEGMENTED_EXPLICIT")) {
                solution.setDistanceType(DistanceType.SEGMENTED_ROAD_DISTANCE);
                String edgeWeightFormat = readStringValue("EDGE_WEIGHT_FORMAT *:");
                if (!edgeWeightFormat.equalsIgnoreCase("HUB_AND_NEARBY_MATRIX")) {
                    throw new IllegalArgumentException("The edgeWeightFormat (" + edgeWeightFormat + ") is not supported.");
                }
            } else {
                throw new IllegalArgumentException("The edgeWeightType (" + edgeWeightType + ") is not supported.");
            }
            solution.setDistanceUnitOfMeasurement(readOptionalStringValue("EDGE_WEIGHT_UNIT_OF_MEASUREMENT *:", "distance"));
            capacity = readIntegerValue("CAPACITY *:");
        }

        private void readVrpWebLocationList() throws IOException {
            DistanceType distanceType = solution.getDistanceType();
            List<HubSegmentLocation> hubLocationList = null;
            locationMap = new LinkedHashMap<Long, Location>(customerListSize);
            if (distanceType == DistanceType.SEGMENTED_ROAD_DISTANCE) {
                int hubListSize= readIntegerValue("HUBS *:");
                hubLocationList = new ArrayList<HubSegmentLocation>(hubListSize);
                readConstantLine("HUB_COORD_SECTION");
                for (int i = 0; i < hubListSize; i++) {
                    String line = bufferedReader.readLine();
                    String[] lineTokens = splitBySpacesOrTabs(line.trim(), 3, 4);
                    HubSegmentLocation location = new HubSegmentLocation();
                    location.setId(Long.parseLong(lineTokens[0]));
                    location.setLatitude(Double.parseDouble(lineTokens[1]));
                    location.setLongitude(Double.parseDouble(lineTokens[2]));
                    if (lineTokens.length >= 4) {
                        location.setName(lineTokens[3]);
                    }
                    hubLocationList.add(location);
                    locationMap.put(location.getId(), location);
                }
            }
            List<Location> customerLocationList = new ArrayList<Location>(customerListSize);
            readConstantLine("NODE_COORD_SECTION");
            for (int i = 0; i < customerListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), 3, 4);
                Location location;
                switch (distanceType) {
                    case AIR_DISTANCE:
                        location = new AirLocation();
                        break;
                    case ROAD_DISTANCE:
                        location = new RoadLocation();
                        break;
                    case SEGMENTED_ROAD_DISTANCE:
                        location = new RoadSegmentLocation();
                        break;
                    default:
                        throw new IllegalStateException("The distanceType (" + distanceType
                                + ") is not implemented.");

                }
                location.setId(Long.parseLong(lineTokens[0]));
                location.setLatitude(Double.parseDouble(lineTokens[1]));
                location.setLongitude(Double.parseDouble(lineTokens[2]));
                if (lineTokens.length >= 4) {
                    location.setName(lineTokens[3]);
                }
                customerLocationList.add(location);
                locationMap.put(location.getId(), location);
            }
            if (distanceType == DistanceType.ROAD_DISTANCE) {
                readConstantLine("EDGE_WEIGHT_SECTION");
                for (int i = 0; i < customerListSize; i++) {
                    RoadLocation location = (RoadLocation) customerLocationList.get(i);
                    Map<RoadLocation, Double> travelDistanceMap = new LinkedHashMap<RoadLocation, Double>(customerListSize);
                    String line = bufferedReader.readLine();
                    String[] lineTokens = splitBySpacesOrTabs(line.trim(), customerListSize);
                    for (int j = 0; j < customerListSize; j++) {
                        double travelDistance = Double.parseDouble(lineTokens[j]);
                        if (i == j) {
                            if (travelDistance != 0.0) {
                                throw new IllegalStateException("The travelDistance (" + travelDistance
                                        + ") should be zero.");
                            }
                        } else {
                            RoadLocation otherLocation = (RoadLocation) customerLocationList.get(j);
                            travelDistanceMap.put(otherLocation, travelDistance);
                        }
                    }
                    location.setTravelDistanceMap(travelDistanceMap);
                }
            }
            if (distanceType == DistanceType.SEGMENTED_ROAD_DISTANCE) {
                readConstantLine("SEGMENTED_EDGE_WEIGHT_SECTION");
                int locationListSize = hubLocationList.size() + customerListSize;
                for (int i = 0; i < locationListSize; i++) {
                    String line = bufferedReader.readLine();
                    String[] lineTokens = splitBySpacesOrTabs(line.trim(), 3, null);
                    if (lineTokens.length % 2 != 1) {
                        throw new IllegalArgumentException("Invalid SEGMENTED_EDGE_WEIGHT_SECTION line (" + line + ").");
                    }
                    long id = Long.parseLong(lineTokens[0]);
                    Location location = locationMap.get(id);
                    if (location == null) {
                        throw new IllegalArgumentException("The location with id (" + id + ") of line (" + line + ") does not exist.");
                    }
                    Map<HubSegmentLocation, Double> hubTravelDistanceMap = new LinkedHashMap<HubSegmentLocation, Double>(lineTokens.length / 2);
                    Map<RoadSegmentLocation, Double> nearbyTravelDistanceMap = new LinkedHashMap<RoadSegmentLocation, Double>(lineTokens.length / 2);
                    for (int j = 1; j < lineTokens.length; j += 2) {
                        Location otherLocation = locationMap.get(Long.parseLong(lineTokens[j]));
                        double travelDistance = Double.parseDouble(lineTokens[j + 1]);
                        if (otherLocation instanceof HubSegmentLocation) {
                            hubTravelDistanceMap.put((HubSegmentLocation) otherLocation, travelDistance);
                        } else {
                            nearbyTravelDistanceMap.put((RoadSegmentLocation) otherLocation, travelDistance);
                        }
                    }
                    if (location instanceof HubSegmentLocation) {
                        HubSegmentLocation hubSegmentLocation = (HubSegmentLocation) location;
                        hubSegmentLocation.setHubTravelDistanceMap(hubTravelDistanceMap);
                        hubSegmentLocation.setNearbyTravelDistanceMap(nearbyTravelDistanceMap);
                    } else {
                        RoadSegmentLocation roadSegmentLocation = (RoadSegmentLocation) location;
                        roadSegmentLocation.setHubTravelDistanceMap(hubTravelDistanceMap);
                        roadSegmentLocation.setNearbyTravelDistanceMap(nearbyTravelDistanceMap);
                    }
                }
            }
            List<Location> locationList;
            if (distanceType == DistanceType.SEGMENTED_ROAD_DISTANCE) {
                locationList = new ArrayList<Location>(hubLocationList.size() + customerListSize);
                locationList.addAll(hubLocationList);
                locationList.addAll(customerLocationList);
            } else {
                locationList = customerLocationList;
            }
            solution.setLocationList(locationList);
        }

        private void readVrpWebCustomerList() throws IOException {
            readConstantLine("DEMAND_SECTION");
            List<Customer> customerList = new ArrayList<Customer>(customerListSize);
            for (int i = 0; i < customerListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), 2);
                Customer customer = new Customer();
                long id = Long.parseLong(lineTokens[0]);
                customer.setId(id);
                Location location = locationMap.get(id);
                if (location == null) {
                    throw new IllegalArgumentException("The customer with id (" + id
                            + ") has no location (" + location + ").");
                }
                customer.setLocation(location);
                int demand = Integer.parseInt(lineTokens[1]);
                customer.setDemand(demand);
                // Notice that we leave the PlanningVariable properties on null
                // Do not add a customer that has no demand
                if (demand != 0) {
                    customerList.add(customer);
                }
            }
            solution.setCustomerList(customerList);
        }

        private void readVrpWebDepotList() throws IOException {
            readConstantLine("DEPOT_SECTION");
            depotList = new ArrayList<Depot>(customerListSize);
            long id = readLongValue();
            while (id != -1) {
                Depot depot = new Depot();
                depot.setId(id);
                Location location = locationMap.get(id);
                if (location == null) {
                    throw new IllegalArgumentException("The depot with id (" + id
                            + ") has no location (" + location + ").");
                }
                depot.setLocation(location);
                depotList.add(depot);
                id = readLongValue();
            }
            solution.setDepotList(depotList);
        }

        private void createVrpWebVehicleList() throws IOException {
            String inputFileName = inputFile.getName();
            if (inputFileName.toLowerCase().startsWith("tutorial")) {
                vehicleListSize = readIntegerValue("VEHICLES *:");
            } else {
                String inputFileNameRegex = "^.+\\-k(\\d+)\\.vrp$";
                if (!inputFileName.matches(inputFileNameRegex)) {
                    throw new IllegalArgumentException("The inputFileName (" + inputFileName
                            + ") does not match the inputFileNameRegex (" + inputFileNameRegex + ").");
                }
                String vehicleListSizeString = inputFileName.replaceAll(inputFileNameRegex, "$1");
                try {
                    vehicleListSize = Integer.parseInt(vehicleListSizeString);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("The inputFileName (" + inputFileName
                            + ") has a vehicleListSizeString (" + vehicleListSizeString + ") that is not a number.", e);
                }
            }
            createVehicleList();
        }

        private void createVehicleList() {
            List<Vehicle> vehicleList = new ArrayList<Vehicle>(vehicleListSize);
            long id = 0;
            for (int i = 0; i < vehicleListSize; i++) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(id);
                id++;
                
                if(id ==0) {
                	vehicle.setCapacity(7);
                } else if(id ==1) {
                	vehicle.setCapacity(17);
                } else if(id ==2) {
                	vehicle.setCapacity(13);
                } else if(id ==3) {
                	vehicle.setCapacity(8);
                } else if(id ==4) {
                	vehicle.setCapacity(20);
                } else {
                	vehicle.setCapacity(capacity);
                }
                vehicle.setDepot(depotList.get(0));
                vehicleList.add(vehicle);
            }
            solution.setVehicleList(vehicleList);
        }

        // ************************************************************************
        // CVRP coursera format. See https://class.coursera.org/optimization-001/
        // ************************************************************************

        public void readCourseraFormat() throws IOException {
            solution.setDistanceType(DistanceType.AIR_DISTANCE);
            solution.setDistanceUnitOfMeasurement("distance");
            List<Location> locationList = new ArrayList<Location>(customerListSize);
            depotList = new ArrayList<Depot>(1);
            List<Customer> customerList = new ArrayList<Customer>(customerListSize);
            locationMap = new LinkedHashMap<Long, Location>(customerListSize);
            for (int i = 0; i < customerListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), 3, 4);
                AirLocation location = new AirLocation();
                location.setId((long) i);
                location.setLatitude(Double.parseDouble(lineTokens[1]));
                location.setLongitude(Double.parseDouble(lineTokens[2]));
                if (lineTokens.length >= 4) {
                    location.setName(lineTokens[3]);
                }
                locationList.add(location);
                if (i == 0) {
                    Depot depot = new Depot();
                    depot.setId((long) i);
                    depot.setLocation(location);
                    depotList.add(depot);
                } else {
                    Customer customer = new Customer();
                    customer.setId((long) i);
                    customer.setLocation(location);
                    int demand = Integer.parseInt(lineTokens[0]);
                    customer.setDemand(demand);
                    // Notice that we leave the PlanningVariable properties on null
                    // Do not add a customer that has no demand
                    if (demand != 0) {
                        customerList.add(customer);
                    }
                }
            }
            solution.setLocationList(locationList);
            solution.setDepotList(depotList);
            solution.setCustomerList(customerList);
            createVehicleList();
        }

        // ************************************************************************
        // CVRPTW normal format. See http://neo.lcc.uma.es/vrp/
        // ************************************************************************

        public void readTimeWindowedFormat() throws IOException {
            readTimeWindowedHeaders();
            readTimeWindowedDepotAndCustomers();
            createVehicleList();
        }

        private void readTimeWindowedHeaders() throws IOException {
            solution.setDistanceType(DistanceType.AIR_DISTANCE);
            solution.setDistanceUnitOfMeasurement("distance");
            readEmptyLine();
            readConstantLine("VEHICLE");
            readConstantLine("NUMBER +CAPACITY");
            String[] lineTokens = splitBySpacesOrTabs(readStringValue(), 2);
            vehicleListSize = Integer.parseInt(lineTokens[0]);
            capacity = Integer.parseInt(lineTokens[1]);
            readEmptyLine();
            readConstantLine("CUSTOMER");
            readConstantLine("CUST\\s+NO\\.\\s+XCOORD\\.\\s+YCOORD\\.\\s+DEMAND\\s+READY\\s+TIME\\s+DUE\\s+DATE\\s+SERVICE\\s+TIME");
            readEmptyLine();
        }

        private void readTimeWindowedDepotAndCustomers() throws IOException {
            String line = bufferedReader.readLine();
            int locationListSizeEstimation = 25;
            List<Location> locationList = new ArrayList<Location>(locationListSizeEstimation);
            depotList = new ArrayList<Depot>(1);
            TimeWindowedDepot depot = null;
            List<Customer> customerList = new ArrayList<Customer>(locationListSizeEstimation);
            boolean first = true;
            while (line != null && !line.trim().isEmpty()) {
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), 7);
                long id = Long.parseLong(lineTokens[0]);
                AirLocation location = new AirLocation();
                location.setId(id);
                location.setLatitude(Double.parseDouble(lineTokens[1]));
                location.setLongitude(Double.parseDouble(lineTokens[2]));
                locationList.add(location);
                int demand = Integer.parseInt(lineTokens[3]);
                long readyTime = Long.parseLong(lineTokens[4]) * 1000L;
                long dueTime = Long.parseLong(lineTokens[5]) * 1000L;
                long serviceDuration = Long.parseLong(lineTokens[6]) * 1000L;
                if (first) {
                    depot = new TimeWindowedDepot();
                    depot.setId(id);
                    depot.setLocation(location);
                    if (demand != 0) {
                        throw new IllegalArgumentException("The depot with id (" + id
                                + ") has a demand (" + demand + ").");
                    }
                    depot.setReadyTime(readyTime);
                    depot.setDueTime(dueTime);
                    if (serviceDuration != 0) {
                        throw new IllegalArgumentException("The depot with id (" + id
                                + ") has a serviceDuration (" + serviceDuration + ").");
                    }
                    depotList.add(depot);
                    first = false;
                } else {
                    TimeWindowedCustomer customer = new TimeWindowedCustomer();
                    customer.setId(id);
                    customer.setLocation(location);
                    customer.setDemand(demand);
                    customer.setReadyTime(readyTime);
                    // Score constraint arrivalAfterDueTimeAtDepot is a build-in hard constraint in VehicleRoutingImporter
                    long maximumDueTime = depot.getDueTime()
                            - serviceDuration - location.getDistanceTo(depot.getLocation());
                    if (dueTime > maximumDueTime) {
                        logger.warn("The customer ({})'s dueTime ({}) was automatically reduced" +
                                " to maximumDueTime ({}) because of the depot's dueTime ({}).",
                                customer, dueTime, maximumDueTime, depot.getDueTime());
                        dueTime = maximumDueTime;
                    }
                    customer.setDueTime(dueTime);
                    customer.setServiceDuration(serviceDuration);
                    // Notice that we leave the PlanningVariable properties on null
                    // Do not add a customer that has no demand
                    if (demand != 0) {
                        customerList.add(customer);
                    }
                }
                line = bufferedReader.readLine();
            }
            solution.setLocationList(locationList);
            solution.setDepotList(depotList);
            solution.setCustomerList(customerList);
            customerListSize = locationList.size();
        }

    }

}
