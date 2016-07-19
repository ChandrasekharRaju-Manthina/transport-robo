/*
 * Copyright 2015 JBoss Inc
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

package com.abcc.trobo.vehiclerouting.domain;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonVehicleRoutingSolution {

    protected String name;
    protected Date tripDate;
    protected String tripType;
    protected Long shiftId;

    protected List<JsonCustomer> customerList;
    protected List<JsonVehicleRoute> vehicleRouteList;

    protected Boolean feasible;
    protected String distance;
    
    protected Boolean isNotAccurate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonCustomer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<JsonCustomer> customerList) {
        this.customerList = customerList;
    }

    public List<JsonVehicleRoute> getVehicleRouteList() {
        return vehicleRouteList;
    }

    public void setVehicleRouteList(List<JsonVehicleRoute> vehicleRouteList) {
        this.vehicleRouteList = vehicleRouteList;
    }

    public Boolean getFeasible() {
        return feasible;
    }

    public void setFeasible(Boolean feasible) {
        this.feasible = feasible;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public Boolean getIsNotAccurate() {
		return isNotAccurate;
	}

	public void setIsNotAccurate(Boolean isNotAccurate) {
		this.isNotAccurate = isNotAccurate;
	}
}
