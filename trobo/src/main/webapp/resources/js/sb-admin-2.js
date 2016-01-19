var intervalTimer, intervalTimer1, clearIntervalTimer, tripSheetData;
function scrollToAnchor(aid){
    var aTag = $("a[name='"+ aid +"']");
    $('html,body').animate({scrollTop: aTag.offset().top},'slow');
}

function resetToAdd(form) {
	$("#form").attr('method','POST');
	$("button[type='submit']").html("Add");
	$("#addLink").hide();
	$("#form")[0].reset();
	if(form == "addressForm") {
    	$("#form").attr('data-success-msg','Address has been created successfully.');
    	$("#header").html("Add Address");    	
	} else if(form == "driverForm") {
		$("#form").attr('data-success-msg','Driver has been created successfully.');
    	$("#header").html("Add Driver");
	} else if(form == "vehicleForm") {
		$("#form").attr('data-success-msg','Vehicle has been created successfully.');
    	$("#header").html("Add Vehicle");
	} else if(form == "shiftForm") {
		$("#form").attr('data-success-msg','Shift has been created successfully.');
    	$("#header").html("Add Shift");
	} else if(form == "transportReqForm") {
		$("#form").attr('data-success-msg','Transport request has been submitted successfully.');
    	$("#header").html("Transport request");
	}
}

var map;
var directionsService;
var vehicleRouteLines;

var vehicleRouteDirections;
var directionsTaskQueue;
var directionsTaskTimer;

resolveDirections = function () {
    if (vehicleRouteLines != undefined) {
      for (var i = 0; i < vehicleRouteLines.length; i++) {
        vehicleRouteLines[i].setMap(null);
      }
    }
    if (vehicleRouteDirections != undefined) {
      for (var i = 0; i < vehicleRouteDirections.length; i++) {
        vehicleRouteDirections[i].setMap(null);
      }
    }
    vehicleRouteLines = undefined;
    vehicleRouteDirections = [];
    directionsTaskQueue = [];
    $.each(tripSheetData.vehicleRouteList, function(index, vehicleRoute) {
      var depotLocation = new google.maps.LatLng(vehicleRoute.depotLatitude, vehicleRoute.depotLongitude);
      var previousLocation = depotLocation;
      $.each(vehicleRoute.customerList, function(index, customer) {
        var location = new google.maps.LatLng(customer.latitude, customer.longitude);
        directionsTaskQueue.push([previousLocation, location, vehicleRoute.hexColor]);
        previousLocation = location;
      });
      //directionsTaskQueue.push([previousLocation, depotLocation, vehicleRoute.hexColor]);
    });
    $('#scoreValue').text(tripSheetData.feasible ? tripSheetData.distance : "Not solved");
    directionsTaskTimer = setInterval(function () {
      sendDirectionsRequest()
    }, 1000); // 1 per second to avoid limit set by Google API for freeloaders
  };
  sendDirectionsRequest = function () {
    var task = directionsTaskQueue.shift();
    if (task == undefined) {
      window.clearInterval(directionsTaskTimer);
      directionsTaskTimer = undefined;
      return;
    }
    var request = {
      origin: task[0],
      destination: task[1],
      travelMode: google.maps.TravelMode.DRIVING
    };
    var hexColor = task[2];
    directionsService.route(request, function(response, status) {
      if (status == google.maps.DirectionsStatus.OK) {
        var directionsRenderer = new google.maps.DirectionsRenderer({
          map: map,
          polylineOptions: {
            geodesic: true,
            strokeColor: hexColor,
            strokeOpacity: 0.8,
            strokeWeight: 4
          },
          markerOptions: {
            visible: false
          },
          preserveViewport: true
        });
        directionsRenderer.setDirections(response);
        vehicleRouteDirections.push(directionsRenderer);
      } else if (status == google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
        console.log("Google directions API request failed wtih status (" + status + "), but retrying...");
        directionsTaskQueue.push(task);
      } else {
        console.log("Google directions API request failed with status (" + status + ").");
      }
    });
  };

  renderDirections = function (origin, destination, hexColor) {
    var request = {
      origin: origin,
      destination: destination,
      travelMode: google.maps.TravelMode.DRIVING
    };
    directionsService.route(request, function(response, status) {
      if (status == google.maps.DirectionsStatus.OK) {
        var directionsRenderer = new google.maps.DirectionsRenderer({
          map: map,
          polylineOptions: {
            geodesic: true,
            strokeColor: hexColor,
            strokeOpacity: 0.8,
            strokeWeight: 4
          },
          markerOptions: {
            visible: false
          }
        });
        directionsRenderer.setDirections(response);
        vehicleRouteDirections.push(directionsRenderer);
      } else {
        console.log("Google directions error status: " + status);
      }
    });
  };
  function sleep(milliseconds) {
    console.log("Sleeping " + milliseconds + " ms");
    // TODO Don't hang the browser page like this
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
      if ((new Date().getTime() - start) > milliseconds) {
        break;
      }
    }
  }

updateMapSolution1 = function(solution) {

	console.log(JSON.stringify(solution));
    if (vehicleRouteLines != undefined) {
      for (var i = 0; i < vehicleRouteLines.length; i++) {
        vehicleRouteLines[i].setMap(null);
      }
    }
    if (vehicleRouteDirections != undefined) {
      for (var i = 0; i < vehicleRouteDirections.length; i++) {
        vehicleRouteDirections[i].setMap(null);
      }
    }
    vehicleRouteLines = [];
    vehicleRouteDirections = undefined;
    $.each(solution.vehicleRouteList, function(index, vehicleRoute) {
      var locations = [new google.maps.LatLng(vehicleRoute.depotLatitude, vehicleRoute.depotLongitude)];
      $.each(vehicleRoute.customerList, function(index, customer) {
        locations.push(new google.maps.LatLng(customer.latitude, customer.longitude));
      });
      //locations.push(new google.maps.LatLng(vehicleRoute.depotLatitude, vehicleRoute.depotLongitude));
      var line = new google.maps.Polyline({
        path: locations,
        geodesic: true,
        strokeColor: vehicleRoute.hexColor,
        strokeOpacity: 0.8,
        strokeWeight: 4
      });
      line.setMap(map);
      vehicleRouteLines.push(line);
    });
    $('#scoreValue').text(solution.feasible ? solution.distance : "Not solved");
};

updateMapSolution = function() {
	var formData = $("#tripSheetForm").serializeObject();
    delete formData['vehicleCapcities'];
    $.ajax({
      url: "tripSheet/getSolution",
      type: "POST",
      dataType : "json",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(formData),
      success: function(solution) {
    	  updateMapSolution1(solution);    	  
      }, error : function(jqXHR, textStatus, errorThrown) {ajaxError(jqXHR, textStatus, errorThrown)}
    });
  };
  
terminateSolution = function() {	
	clearInterval(intervalTimer);
	clearInterval(clearIntervalTimer);
	$("#loadIcon").hide();
}
updateSolution = function() {
	clearInterval(intervalTimer);
	clearInterval(intervalTimer1);
	$("#tablesDiv").html("");
	var vehicles = []; 
    $("#vehicles :selected").each(function(i, selected){ 
    	var vehicle = {};
    	vehicle.id = $(selected).attr("data-id");
    	vehicle.vehicleNumber = $(selected).attr("data-vehicle-number");
    	vehicle.seats = $(selected).val();
    	vehicles.push(vehicle);
    });
    
    var formData = $("#tripSheetForm").serializeObject();
    formData.vehicles = vehicles;
    delete formData['vehicleCapcities'];
    $.ajax({
      type: $("#tripSheetForm").attr("method"),
      url: "tripSheet",
      dataType : "json",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(formData),
      success: function(solution) {
    	  updateMapSolution1(solution); 
    	  if(solution.isNotAccurate == true) {
    		  $("#errMsgMsgTxt").text("Some of the routes are not accurate. They are taking more time than the expected to reach.");
    		  $("#errMsg").show();
    	  }
    	  
    	  if(solution.feasible == false) {
    			console.log("validation errors.");
    			$("#tablesDiv").html("");
    			$("#loadIcon").hide();
    			$("#errMsgMsgTxt").text("Algorithm could not generate the tripsheet.");
    			$("#errMsg").show();
    			$("#saveTripSheet").hide();
    	  } else {
    		  	$("#saveTripSheet").show();
    	  }
    	  
    	  tripSheetData = solution;
    	  console.log(JSON.stringify(solution));
    	  $("#loadIcon").hide();
    	  $.each(solution.vehicleRouteList, function( index, vehicleRoute ) {
    		  
    		  var employees = [];
    		  
    		  $.each(vehicleRoute.customerList, function( index, customer ) {
    			  $.each(customer.employees, function( index, employee ) {
    				  employee.location = customer.locationName;
    				  employee.time = customer.time;    					  
    				  employees.push(employee);
        		  });
    		  });
    		  
    		  var panelStart = '<div class="row"><div class="col-lg-12"><div class="panel panel-default"><div class="panel-heading">'+
		              '<strong>Vehicle number: ' + vehicleRoute.vehicleNumber + ',&nbsp;&nbsp;Capacity:' + vehicleRoute.capacity +'</strong>&nbsp;&nbsp;&nbsp;<button data-id="' + 
		              vehicleRoute.id + '" type="button" class="btn btn-primary btn-sm vehicleRouteMap" data-toggle="modal" data-target="#myModal">Route</button></div><div class="panel-body"><div class="table-responsive">'; 
			  var panelEnd = '</div></div></div>';
			  $("#tablesDiv").append(panelStart + '<table id="data-table' + index + '" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%"></table>' + panelEnd);
			  $("#data-table" + index).DataTable({
	    	        data: employees,
	    	        bPaginate : false,
	    	        bSort : false,
	    	        columns: [
	    	            { title: "Id", "data": "id", "width": "10%" },
	    	            { title: "Name", "data": "name", "width": "30%" },
	    	            { title: "Sex", "data": "sex", "width": "10%" },
	    	            { title: "Location", "data": "location", "width": "35%" },
	    	            { title: "Time", "data": "time", "width": "15%" }
	    	        ]
	    	    });
    		});
    	  $("#tripSheetData").show();
      }, error : function(jqXHR, textStatus, errorThrown) {
    	  alert(errorThrown);
      }
    });
  };

$(function() {

    $('#side-menu').metisMenu();
    
  //triggered when modal is about to be shown
    $('#myModal').on('show.bs.modal', function(e) {

        //get data-id attribute of the clicked element
        var vehicleId = $(e.relatedTarget).data('id');
        console.log(vehicleId);
        
        var latLng = new google.maps.LatLng(tripSheetData.vehicleRouteList[0].depotLatitude, 
        		tripSheetData.vehicleRouteList[0].depotLongitude);
        var vehicleRouteMapCanvas = document.getElementById('vehicle-route-map-canvas');
        var mapOptions = {
          center:latLng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        
//        var zoomBounds = new google.maps.LatLngBounds ();
        
        
        var vehilceRoutemap = new google.maps.Map(vehicleRouteMapCanvas, mapOptions);
//          zoomBounds.extend(latLng);
          var marker = new google.maps.Marker({
            position: latLng,
            title: tripSheetData.vehicleRouteList[0].depotLocationName,
            map: vehilceRoutemap
          });
          google.maps.event.addListener(marker, 'click', function() {
            new google.maps.InfoWindow({
              content: tripSheetData.vehicleRouteList[0].depotLocationName
            }).open(vehilceRoutemap,marker);
          })
        //populate the textbox
        //$(e.currentTarget).find('input[name="bookId"]').val(bookId);
    });
    
    $(document).on("click", "a.delete", function(e){
    	var $this = $(this);
    	e.preventDefault();
    	$.ajax({
            type: "DELETE",
            url: $(this).attr("data-url") + "/" + $(this).attr("data-id"),
            success: function(data)
            {
            	$("#data-table").DataTable().row($this.parents("tr")).remove().draw();
            	$("#successMsgTxt").text($this.attr("data-success-msg"));
            	$("#successMsg").show();
            	$("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
            },
            error: function(request,status,errorThrown) {
            	alert("Error");
            }
        });
    });
    
    $(document).on("click", "a.approve", function(e){
    	var $this = $(this);
    	e.preventDefault();
    	$.ajax({
            type: "PUT",
            url: $(this).attr("data-url") + "/" + $(this).attr("data-id"),
            success: function(data)
            {
            	$("#data-table").DataTable().row($this.parents("tr")).remove().draw();
            	$("#successMsgTxt").text($this.attr("data-success-msg"));
            	$("#successMsg").show();
            	$("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
            },
            error: function(request,status,errorThrown) {
            	alert("Error");
            }
        });
    });
    
    $(document).on("click", "a.update", function(e){
    	e.preventDefault();
    	var formName = $("#form").attr("name");
    	$("#form").attr('method','PUT');
    	$("button[type='submit']").html("Update");
    	if(formName == "addressForm") {
	    	var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
	    	$("input[name='addressLine']").val(data.addressLine);
	    	$("input[name='city']").val(data.city);
	    	$("input[name='state']").val(data.state);
	    	$("input[name='zip']").val(data.zip);
	    	$("input[name='country']").val(data.country);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Address has been updated successfully.');
	    	$("#header").html("Update Address");	    	
    	} else if(formName == "driverForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("input[name='name']").val(data.name);
	    	$("input[name='licenseNumber']").val(data.licenseNumber);
	    	$("input[name='phoneNumber']").val(data.phoneNumber);
	    	$("input[name='yearsOfExperience']").val(data.yearsOfExperience);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Address has been updated successfully.');
	    	$("#header").html("Update Driver details");
    	} else if(formName == "vehicleForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("input[name='vehicleNumber']").val(data.vehicleNumber);
	    	$("input[name='seats']").val(data.seats);
	    	$("input[name='trackingDeviceLink']").val(data.trackingDeviceLink);
	    	$("select[name='driverId']").val(data.driverId);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Vehicle has been updated successfully.');
	    	$("#header").html("Update Vehicle details");
    	} else if(formName == "shiftForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("input[name='startTime']").val(data.startTime);
	    	$("input[name='endTime']").val(data.endTime);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Shift has been updated successfully.');
	    	$("#header").html("Update Shift details");
    	} else if(formName == "transportReqForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("select[name='requestType']").val(data.requestType);
	    	$("select[name='shiftId']").val(data.shiftId);
	    	$("input[name='startDate']").val(data.startDate);
	    	$("input[name='endDate']").val(data.endDate);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Transport request has been updated successfully.');
	    	$("#header").html("Update Transport request details");
    	}
    	$("#addLink").show();
    	scrollToAnchor("addMenu");
    });
    
    $("#tripSheetForm").submit(function (e) {
        e.preventDefault();
        console.log(JSON.stringify($("#tripSheetForm").serializeObject()));
        
        
        
        var vehicles = []; 
        $("#vehicles :selected").each(function(i, selected){ 
        	var vehicle = {};
        	vehicle.id = $(selected).attr("data-id");
        	vehicle.vehicleNumber = $(selected).attr("data-vehicle-number");
        	vehicle.seats = $(selected).val();
        	vehicles.push(vehicle);
        });
        
        var formData = $("#tripSheetForm").serializeObject();
        formData.vehicles = vehicles;
        delete formData['vehicleCapcities'];
        $("#loadIcon").show();
        $("#errMsg").hide();
        $.ajax({
            type: $("#tripSheetForm").attr("method"),
            url: $("#tripSheetForm").attr("action"),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(formData),
            success: function(data)
            {
            	console.log(data);
            	if(data.success == false) {
            		console.log("validation errors.");
            		$("#tablesDiv").html("");
            		$("#loadIcon").hide();
            		$("#errMsgMsgTxt").text(data.status);
            		$("#errMsg").show();
            	} else {
            		intervalTimer1 = setInterval(function () {
            			updateMapSolution()
        	        }, 1000);
            		
	            	intervalTimer = setInterval(function () {
	                    updateSolution()
	                }, 20000);
	            }
//            	clearIntervalTimer = setInterval(function () {
//                    terminateSolution()
//                }, 120000);
            },
            error: function(request,status,errorThrown) {
              alert(errorThrown);
            }
        });
    });
    
    
    
    $("#viewCabForm").submit(function (e) {
    	e.preventDefault();
    	$.ajax({
            type: 'POST',
            url: 'tripSheet/findCab',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify($("#viewCabForm").serializeObject()),
            success: function(data)
            {
            	console.log(data);  
            	if(data.status == "Not found") {
            		$("#vehicleDetails").hide();
            		$("#message").show();
            	} else {
	            	$("#vehicleNumber").text(data.vehicleNumber);
	            	$("#driverName").text(data.driver.name);
	            	$("#driverNumber").text(data.driver.phoneNumber);
	            	$("#track").text(data.trackingDeviceLink);
	            	$("#vehicleDetails").show();
	            	$("#message").hide();
            	}
            },
            error: function(request,status,errorThrown) {
              alert(errorThrown);
            }
        });
    });
    
    $("#map").click(function (e) {
    	var mapCanvas = document.getElementById('map-canvas');
        var mapOptions = {
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(mapCanvas, mapOptions);
        directionsService = new google.maps.DirectionsService();
        
        var vehicles = []; 
        $("#vehicles :selected").each(function(i, selected){ 
        	var vehicle = {};
        	vehicle.id = $(selected).attr("data-id");
        	vehicle.vehicleNumber = $(selected).attr("data-vehicle-number");
        	vehicle.seats = $(selected).val();
        	vehicles.push(vehicle);
        });
        
        var formData = $("#tripSheetForm").serializeObject();
        formData.vehicles = vehicles;
        delete formData['vehicleCapcities'];
    	 $.ajax({
    	      url: "tripSheet/solution",
    	      type: "POST",
    	      dataType: "json",
              contentType: "application/json;charset=utf-8",
              data: JSON.stringify(formData),
    	      success: function(solution) {
    	        var zoomBounds = new google.maps.LatLngBounds ();
    	        
    	        var addressCapacity = {};
    	        $.each(solution.customerList, function(index, customer) {
    	        	if(addressCapacity[customer.id]) {
    	        		addressCapacity[customer.id] = addressCapacity[customer.id] + 1;
    	        	} else {
    	        		addressCapacity[customer.id] = 1;
    	        	}
    	        });
    	        
    	        $.each(solution.customerList, function(index, customer) {
    	          var latLng = new google.maps.LatLng(customer.latitude, customer.longitude);
    	          zoomBounds.extend(latLng);
    	          var marker = new google.maps.Marker({
    	            position: latLng,
    	            title: customer.locationName + ": Total Employees: " + addressCapacity[customer.id],
    	            map: map
    	          });
    	          google.maps.event.addListener(marker, 'click', function() {
    	            new google.maps.InfoWindow({
    	              content: customer.locationName + "</br>Total Employees:  " + addressCapacity[customer.id]
    	            }).open(map,marker);
    	          })
    	        });
    	        

  	          var latLng = new google.maps.LatLng(solution.vehicleRouteList[0].depotLatitude, solution.vehicleRouteList[0].depotLongitude);
  	          zoomBounds.extend(latLng);
  	          var marker = new google.maps.Marker({
  	            position: latLng,
  	            title: solution.vehicleRouteList[0].depotLocationName,
  	            map: map
  	          });
  	          google.maps.event.addListener(marker, 'click', function() {
  	            new google.maps.InfoWindow({
  	              content: solution.vehicleRouteList[0].depotLocationName
  	            }).open(map,marker);
  	          })
  	          
  	          map.fitBounds(zoomBounds);
    	      }, error : function(jqXHR, textStatus, errorThrown) {alert(errorThrown);}
    	    });
    });
    
    $("#countEmployees").click(function (e) {
    	var $myForm = $("#tripSheetForm");
    	$("#vehicles").hide();
    	if (!$myForm[0].checkValidity()) {
		  // If the form is invalid, submit it. The form won't actually submit;
		  // this will just cause the browser to display the native HTML5 error messages.
		  $myForm.find(":submit").click();
		}
    	$("#vehicles").show();
    	
    	var formData = $("#tripSheetForm").serializeObject();
        delete formData['vehicleCapcities'];
         
    	$.ajax({
            type: 'POST',
            url: 'tripSheet/count',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(formData),
            success: function(data)
            {
            	 $("#totalEmployees").text(data);
            },
            error: function(request,status,errorThrown) {
              alert(errorThrown);
            }
        });
    	
    });
    
    $("#saveTripSheet").click(function (e) {
    	console.log(JSON.stringify(tripSheetData))
    	$.ajax({
            type: 'POST',
            url: 'tripSheet/save',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(tripSheetData),
            success: function(data)
            {
            	console.log(data);          	
            	$("#tripSheetData").hide();
            	$("#tripSheetForm")[0].reset();
            	$("#totalSeats").text(0);
            	$("#totalEmployees").text(0); 
            	$("#successMsg").show(); 
            	$("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
            },
            error: function(request,status,errorThrown) {
              alert(errorThrown);
            }
        });
    });
    
    $("#tripDate, #shiftId, #drop").change(function(){
    	$("#totalEmployees").text(0);       
    });
    
    $("#vehicles").change(function(){
    	var totalSeats = 0;
        $("#vehicles :selected").each(function(i, selected){
        	totalSeats = totalSeats + parseInt($(selected).val());
        });
        $("#totalSeats").text(totalSeats);        
    });
    
    $("#viewTripSheetForm").submit(function (e) {
    	e.preventDefault();
    	$("#tablesDiv").html("");
    	$.ajax({
            type: 'POST',
            url: 'tripSheet/get',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify($("#viewTripSheetForm").serializeObject()),
            success: function(solution)
            {
            	console.log(solution);            	
            	if (typeof solution.vehicleRouteList !== 'undefined' && solution.vehicleRouteList.length > 0) {
            		$.each(solution.vehicleRouteList, function( index, vehicleRoute ) {
                		  
                		  var employees = [];
                		  
                		  $.each(vehicleRoute.customerList, function( index, customer ) {
                			  $.each(customer.employees, function( index, employee ) {
                				  employee.location = customer.locationName;
                				  employee.time = customer.time;    					  
                				  employees.push(employee);
                    		  });
                		  });
                		  
                		  var panelStart = '<div class="row"><div class="col-lg-12"><div class="panel panel-default"><div class="panel-heading">'+
		                          '<strong>Vehicle number: ' + vehicleRoute.vehicleNumber + '</strong></div><div class="panel-body"><div class="table-responsive">'; 
		        		  var panelEnd = '</div></div></div>';
		        		  $("#tablesDiv").append(panelStart + '<table id="data-table' + index + '" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%"></table>' + panelEnd);
		    	   	      $("#data-table" + index).DataTable({
            	    	        data: employees,
            	    	        bPaginate : false,
            	    	        bSort : false,
            	    	        columns: [
            	    	            { title: "Id", "data": "id", "width": "10%" },
				    	            { title: "Name", "data": "name", "width": "35%" },
				    	            { title: "Gender", "data": "sex", "width": "10%" },
				    	            { title: "Location", "data": "location", "width": "35%" },
				    	            { title: "Time", "data": "time", "width": "15%" }
            	    	        ]
            	    	    });
                		});
                	  $("#tripSheetData").show();
                	  $("#message").hide(); 
            	} else {
            		$("#tripSheetData").hide();
            		$("#message").show();            		
            	}           	
            	
            },
            error: function(request,status,errorThrown) {
              alert(errorThrown);
            }
        });
    });
    
    $("#exportTripSheet").click(function (e) {    
    	var date = new Date($("#tripSheetDate").val()).getTime();
    	var shiftId = $("#shiftId").val();
    	var isDrop = $("#drop").val();
    	console.log('http://localhost:8080/trobo/tripSheet/export?date='+ 
    			date + '&id=' + shiftId + '&isDrop=' + isDrop, '_blank');
    	window.open('http://localhost:8080/trobo/tripSheet/export?date='+ 
    			date + '&id=' + shiftId + '&isDrop=' + isDrop, '_blank');
    	
//    	$("#tripSheetDate").val(date.getDate() + '-' + (date.getMonth() + 1) + '-' +  date.getFullYear());
//    	var date = new Date($("#tripSheetDate").val());
//    	$("#tripSheetDate").val(date.getDate() + '-' + (date.getMonth() + 1) + '-' +  date.getFullYear());
//    	$("#tripSheetDate").val("");
    });
    
    $("#employeeForm").submit(function (e) {
    	e.preventDefault();
    	 $.ajax({
             type: $("#employeeForm").attr("method"),
             url: $("#employeeForm").attr("action"),
             dataType: "json",
             contentType: "application/json;charset=utf-8",
             data: JSON.stringify($("#employeeForm").serializeObject()), // serializes the form's elements.
             success: function(data)
             {
            	// similar behavior as clicking on a link
            	 window.location.href = "home";
             },
             error: function(request,status,errorThrown) {
                alert("server error");
             }
         });
    });
    
    $("#form").submit(function (e) {
        e.preventDefault();
        $(".error-msg").hide();
        $("div").removeClass("has-error");
        $.ajax({
            type: $("#form").attr("method"),
            url: $("#form").attr("action"),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify($("#form").serializeObject()), // serializes the form's elements.
            success: function(data)
            {
            	$("#successMsgTxt").text($("#form").attr("data-success-msg"));
                $("#successMsg").show();
                $("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
                $("#form")[0].reset();
                $("#data-table").DataTable().ajax.reload();
                resetToAdd($("#form").attr("name"));
            },
            error: function(request,status,errorThrown) {
               if(request.status = 400) {
            	   $.each(request.responseJSON.fieldErrors, function(i, item) {
            		   	var id = "#"+item.field +"-error"
            		    $(id).text(item.message)
            		    var parent = $(id).closest("div")
            		    parent.addClass("has-error");
            		    $(id).show();
            	   });            	   
               }
            }
        });
    });

});

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
	
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        return this.href == url || url.href.indexOf(this.href) == 0;
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }
});
