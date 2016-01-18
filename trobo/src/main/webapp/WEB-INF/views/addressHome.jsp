<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<style>
.controls {
  margin-top: 10px;
  border: 1px solid transparent;
  border-radius: 2px 0 0 2px;
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  height: 32px;
  outline: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

#pac-input {
  background-color: #fff;
  font-family: Roboto;
  font-size: 15px;
  font-weight: 300;
  margin-left: 12px;
  padding: 0 11px 0 13px;
  text-overflow: ellipsis;
  width: 300px;
}

#pac-input:focus {
  border-color: #4d90fe;
}

.pac-container {
  font-family: Roboto;
}

#type-selector {
  color: #fff;
  background-color: #4d90fe;
  padding: 5px 11px 0px 11px;
}

#type-selector label {
  font-family: Roboto;
  font-size: 13px;
  font-weight: 300;
}

    </style>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<s:message code="address.header" text="Welcome" />
		</h3>
		<ol class="breadcrumb" id="addLink" style="display: none;">
            <li>
                <i class="fa fa-home"></i>  <a href="#" name="addMenu" onclick="resetToAdd('addressForm')">Add address</a>
            </li>
        </ol>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
	  <input id="pac-input" class="controls" type="text" placeholder="Search Box">
      <div id="map" style="height: 600px; width: 100%; margin-top: 10px; margin-bottom: 10px;"></div>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Add Address</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="addressForm" method="POST" action="addresses" data-success-msg="Address has been added successfully.">
                	 <input type="hidden" id ="id" name="id">
                	 <input type="hidden" id ="latitude" name="latitude">
                	 <input type="hidden" id ="longitude" name="longitude">
                	 
                	 <div class="row">
                	 	<div class="col-lg-12">
                			<div class="form-group">
                                <label>Address line</label>
                                <input class="form-control" id="addressLine" name="addressLine" placeholder="Enter address line" onFocus="geolocate()">
                                <p class="help-block error-msg" id="addressLine-error" style="display: none;"></p>
                            </div>
                		</div>		
                	 </div>
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>City</label>
                                <input class="form-control" id="administrative_area_level_2" name="city" placeholder="Enter city">
                                <p class="help-block error-msg" id="city-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>State</label>
                                <input class="form-control" id="administrative_area_level_1" name="state" placeholder="Enter state">
                                <p class="help-block error-msg" id="state-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Zip</label>
                                <input class="form-control" id="postal_code" name="zip" placeholder="Enter zip">
                                <p class="help-block error-msg" id="zip-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Country</label>
                                <input class="form-control" id="country" name="country" placeholder="Enter country">
                                <p class="help-block error-msg" id="country-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	<div class="row">
                		<div class="col-lg-12 text-center">
                			<button type="submit" style="width: 80px;" class="btn btn-primary">Add</button>
                    		<button type="reset" style="width: 80px;" class="btn btn-primary">Reset</button>
                    	</div>
                	</div>
                	
                </form>
                <!-- /.row (nested) -->
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row" id="approveMsg" style="display: none;">
    <div class="col-lg-12">           
		<div class="alert alert-success">
    		<p class="fa fa-check text-success" id="approveMsgTxt"></p> 
    	</div>
	</div>
</div>
<div class="row" id="successMsg" style="display: none;">
	<div class="col-lg-12">           
		<div class="alert alert-success">
     		<p class="fa fa-check text-success" id="successMsgTxt"></p> 
     		
		</div>
	</div>
</div>
<% if (request.isUserInRole("admin")) { %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong>Avilable addresses</strong>
            </div>
            <div class="panel-body">
            	<table id="data-table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
			        <thead>
			            <tr>
			                <th>Address</th>
			                <th>City</th>
			                <th>State</th>
			                <th>Zip</th>
			                <th>Country</th>
			                <th>Latitude</th>
			                <th>Longitude</th>
			                <th>Action</th>
			            </tr>
			        </thead>
			        <tfoot>
			        </tfoot>
			    </table>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<%} %>
<% 
	Boolean employeeRole = request.isUserInRole("employee");
	Boolean adminRole = request.isUserInRole("admin");
%> 
<script>
// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.

var placeSearch, autocomplete;
var componentForm = {
  administrative_area_level_2: 'long_name',
  administrative_area_level_1: 'short_name',
  country: 'long_name',
  postal_code: 'short_name'
};

function initAutocomplete() {
  // Create the autocomplete object, restricting the search to geographical
  // location types.
  autocomplete = new google.maps.places.Autocomplete(
      /** @type {!HTMLInputElement} */(document.getElementById('addressLine')),
      {types: ['geocode']});

  // When the user selects an address from the dropdown, populate the address
  // fields in the form.
  autocomplete.addListener('place_changed', fillInAddress);
}

// [START region_fillform]
function fillInAddress() {
  // Get the place details from the autocomplete object.
  var place = autocomplete.getPlace();
  
 // document.getElementById('latitude').value = autocomplete.getPlace().geometry.location.lat();
  //document.getElementById('longitude').value = autocomplete.getPlace().geometry.location.lng();

  for (var component in componentForm) {
    //document.getElementById(component).value = '';
    //document.getElementById(component).disabled = false;
  }

  // Get each component of the address from the place details
  // and fill the corresponding field on the form.
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    if (componentForm[addressType]) {
      var val = place.address_components[i][componentForm[addressType]];
     // document.getElementById(addressType).value = val;
    }
  }
}
// [END region_fillform]

// [START region_geolocation]
// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      //document.getElementById('latitude').value = position.coords.latitude;
     // document.getElementById('longitude').value = position.coords.longitude;
      var geolocation = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      var circle = new google.maps.Circle({
        center: geolocation,
        radius: position.coords.accuracy
      });
      autocomplete.setBounds(circle.getBounds());
    });
  }
}
// [END region_geolocation]

    </script>`
    
 <script>
	// This example adds a search box to a map, using the Google Place Autocomplete
	// feature. People can enter geographical searches. The search box will return a
	// pick list containing a mix of places and predicted search terms.
	
	
	var marker, map, geocoder;
	
	function geocodePosition(pos) {
	  if(!geocoder) {
		geocoder = new google.maps.Geocoder();
	  }
	  geocoder.geocode({
	    latLng: pos
	  }, function(responses) {
	    if (responses && responses.length > 0) {
	      updateMarkerAddress(responses[0].formatted_address, responses[0].address_components);
	    } else {
	      updateMarkerAddress('Cannot determine address at this location.', null);
	    }
	  });
	}
	
	function updateMarkerPosition(latLng) {
	  document.getElementById('latitude').value = latLng.lat();
  	  document.getElementById('longitude').value = latLng.lng();
	}
	
	var infowindow;
	function updateMarkerAddress(str, address_components) {
	document.getElementById('addressLine').value = str;
	for (var component in componentForm) {
	    document.getElementById(component).value = '';
	    document.getElementById(component).disabled = false;
	    if(address_components) {
		    for (var i = 0; i < address_components.length; i++)  {
		    	var addressType = address_components[i].types[0];
		    	if(addressType === component)  {
		    		document.getElementById(component).value = address_components[i].long_name;
		    	}
		    }
	    }
	    
	}
	  
	 // if(address_components) {
		  //for (var i = 0; i < address_components.length; i++) {
		    //var addressType = address_components[i].types[0];
		    //if (componentForm[addressType]) {
		      //var val = address_components[i][componentForm[addressType]];
		      //document.getElementById(addressType).value = val;
		    //}
		  //}
		  
		  //document.getElementById('addressLine').value = address_components[0].long_name + "," + address_components[1].long_name ;
          //document.getElementById('administrative_area_level_2').value = address_components[2].long_name;
          //document.getElementById('administrative_area_level_1').value = address_components[3].long_name;
         // document.getElementById('postal_code').value = address_components[4].long_name;
          //document.getElementById('country').value = address_components[5].long_name;
		  
	 // }
	  
	  if(!infowindow) {
		infowindow = new google.maps.InfoWindow();
	  }
	  infowindow.setContent(str);
	  infowindow.open(map, marker);
	}
	function initAutocomplete() {
	  map = new google.maps.Map(document.getElementById('map'), {
	    center: {lat: 12.925692673182615, lng: 77.68584288954924},
	    zoom: 13,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	  });
	
	  // Create the search box and link it to the UI element.
	  var input = document.getElementById('pac-input');
	  var searchBox = new google.maps.places.SearchBox(input);
	  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	
	  // Bias the SearchBox results towards current map's viewport.
	  map.addListener('bounds_changed', function() {
	    searchBox.setBounds(map.getBounds());
	  });
	
	  var markers = [];
	  // [START region_getplaces]
	  // Listen for the event fired when the user selects a prediction and retrieve
	  // more details for that place.
	  searchBox.addListener('places_changed', function() {
	    var places = searchBox.getPlaces();
	
	    if (places.length == 0) {
	      return;
	    }
	
	    // Clear out the old markers.
	    markers.forEach(function(marker) {
	      marker.setMap(null);
	    });
	    markers = [];
	
	    // For each place, get the icon, name and location.
	    var bounds = new google.maps.LatLngBounds();
	    places.forEach(function(place) {
	      var icon = {
	        url: place.icon,
	        size: new google.maps.Size(71, 71),
	        origin: new google.maps.Point(0, 0),
	        anchor: new google.maps.Point(17, 34),
	        scaledSize: new google.maps.Size(25, 25)
	      };
		  
		  marker = new google.maps.Marker({
			position: place.geometry.location,
			title: place.name,
			map: map,
			draggable: true
		  });
		  
		  updateMarkerPosition(place.geometry.location);
		  updateMarkerAddress(place.formatted_address, place.address_components);
		  
		   // Add dragging event listeners.
		  google.maps.event.addListener(marker, 'dragstart', function() {
			//updateMarkerAddress('Dragging...');
		  });
	
		  google.maps.event.addListener(marker, 'drag', function() {
			updateMarkerPosition(marker.getPosition());
		  });
	
		  google.maps.event.addListener(marker, 'dragend', function() {
			geocodePosition(marker.getPosition());
		  });
	
	      // Create a marker for each place.
	      markers.push(new google.maps.Marker({
	        map: map,
	        icon: icon,
	        title: place.name,
	        position: place.geometry.location
	      }));
	
	      if (place.geometry.viewport) {
	        // Only geocodes have viewport.
	        bounds.union(place.geometry.viewport);
	      } else {
	        bounds.extend(place.geometry.location);
	      }
	    });
	    map.fitBounds(bounds);
	  });
	  // [END region_getplaces]
	}


    </script>
    
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places&callback=initAutocomplete"
        async defer></script>
<script>
	$(document).ready(function() {
		if (<%=adminRole%>) { 
			loadAddresses();
		}
		if (<%=employeeRole%>) { 
			loadAddressForUser();
		}
	});
	
	function loadAddresses() {
		$("#data-table").dataTable({
    		"columnDefs": [ {
			    "targets": 7,
			    "render": function ( data, type, full, meta ) {
				      return '<a title="update" class="update" href="#" data-id="' + data +'"><span class="glyphicon glyphicon-edit update">Edit&nbsp;</span></a>' + '<a title="delete" class="delete" href="#" data-url="addresses" data-success-msg="Address has been deleted successfully." data-id="' + data +'"><span class="glyphicon glyphicon-remove-sign delete">Delete&nbsp;</span></a>';
			    }
			}],
            "ajax": {
		       "url": "addresses",
		    },
		    "sAjaxDataProp":"",
	        "columns": [
	            { "data": "addressLine" },
	            { "data": "city" },
	            { "data": "state" },
	            { "data": "zip" },
	            { "data": "country" },
	            { "data": "latitude" },
	            { "data": "longitude" },
	            { "data": "id" }
	        ]
   		});
	}
	
	function loadAddressForUser() {
		
		//var empid = $(this).attr('empid');
		var empid = 1;
        $.ajax({
            url: "addresses/user/" + empid,
            contentType: "application/json",
            cache: false,
            method: 'GET',
            error: function() {
            
            },
            success: function(response) {
            	document.getElementById('addressLine').value = response.addressLine;
                document.getElementById('administrative_area_level_2').value = response.city;
                document.getElementById('administrative_area_level_1').value = response.state;
                document.getElementById('id').value = response.id;
                document.getElementById('postal_code').value = response.zip;
                document.getElementById('country').value = response.country;
                $("button[type='submit']").html("Update");
                $("#form").attr('method','PUT');
                $("#form").attr('data-success-msg','Address has been updated successfully.');
                $("#header").html("Update Address");	
            }
        });
	}
</script>