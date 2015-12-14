<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<s:message code="vehicle.header" text="Welcome" />
		</h3>
		<ol class="breadcrumb" id="addLink" style="display: none;">
            <li>
                <i class="fa fa-dashboard"></i>  <a href="#" name="addMenu" onclick="resetToAdd('vehicleForm')">Add vehicle</a>
            </li>
        </ol>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Add Vehicle</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="vehicleForm" method="POST" action="vehicles" data-success-msg="Vehicle has been added successfully.">
                	 <input type="hidden" name="id">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Vehicle number</label>
                                <input class="form-control" name="vehicleNumber" placeholder="Enter vehicle number">
                                <p class="help-block error-msg" id="vehicleNumber-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Capacity</label>
                                <input class="form-control" name="seats" placeholder="Enter capacity">
                                <p class="help-block error-msg" id="seats-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Tracking device link</label>
                                <input class="form-control" name="trackingDeviceLink" placeholder="Enter tracking device link">
                                <p class="help-block error-msg" id="trackingDeviceLink-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Driver name</label>
                                <input class="form-control" name="driverId" placeholder="Enter drivers name">
                                <p class="help-block error-msg" id="driverId-error" style="display: none;"></p>
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

<div class="row" id="successMsg" style="display: none;">
	<div class="col-lg-12">           
		<div class="alert alert-success">
     		<p class="fa fa-check text-success" id="successMsgTxt"></p> 
     		
		</div>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong>Vehicle details</strong>
            </div>
            <div class="panel-body">
            	<table id="data-table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
			        <thead>
			            <tr>
			                <th>Vehicle number</th>
			                <th>Capacity</th>
			                <th>Tracking device link</th>
			                <th>Driver name</th>
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
<script>
	$(document).ready(function() {
		
	    $("#data-table").dataTable({
	    		"columnDefs": [ {
				    "targets": 4,
				    "render": function ( data, type, full, meta ) {
					      return '<a title="update" class="update" href="#" data-id="' + data +'"><span class="glyphicon glyphicon-edit update">Edit&nbsp;</span></a>' + '<a title="delete" class="delete" href="#" data-url="vehicles" data-success-msg="Vehicle has been deleted successfully." data-id="' + data +'"><span class="glyphicon glyphicon-remove-sign delete">Delete</span></a>';
				    }
				}],
	            "ajax": {
			       "url": "vehicles",
			    },
			    "sAjaxDataProp":"",
		        "columns": [
		            { "data": "vehicleNumber" },
		            { "data": "seats" },
		            { "data": "trackingDeviceLink" },
		            { "data": "driverId" },
		            { "data": "id" }
		        ]
	    });
	});
</script>