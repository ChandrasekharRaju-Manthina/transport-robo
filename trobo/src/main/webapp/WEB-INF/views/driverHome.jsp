<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<s:message code="driver.header" text="Welcome" />
		</h3>
		<ol class="breadcrumb" id="addLink" style="display: none;">
            <li>
                <i class="fa fa-dashboard"></i>  <a href="#" name="addMenu" onclick="resetToAdd('driverForm')">Add driver</a>
            </li>
        </ol>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Add Driver</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="driverForm" method="POST" action="drivers" data-success-msg="Driver has been added successfully.">
                	 <input type="hidden" name="id">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Name</label>
                                <input class="form-control" name="name" placeholder="Enter name">
                                <p class="help-block error-msg" id="name-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>License number</label>
                                <input class="form-control" name="licenseNumber" placeholder="Enter license number">
                                <p class="help-block error-msg" id="licenseNumber-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Phone Number</label>
                                <input class="form-control" name="phoneNumber" placeholder="Enter Phone Number">
                                <p class="help-block error-msg" id="phoneNumber-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Years of experience</label>
                                <input class="form-control" name="yearsOfExperience" placeholder="Enter years of experience">
                                <p class="help-block error-msg" id="yearsOfExperience-error" style="display: none;"></p>
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
               <strong>Drivers details</strong>
            </div>
            <div class="panel-body">
            	<table id="data-table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
			        <thead>
			            <tr>
			                <th>Name</th>
			                <th>License number</th>
			                <th>Phone number</th>
			                <th>Years of experience</th>
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
					      return '<a title="update" class="update" href="#" data-id="' + data +'"><span class="glyphicon glyphicon-edit update">Edit&nbsp;</span></a>' + '<a title="delete" class="delete" href="#" data-url="drivers" data-success-msg="Driver has been deleted successfully." data-id="' + data +'"><span class="glyphicon glyphicon-remove-sign delete">Delete</span></a>';
				    }
				}],
	            "ajax": {
			       "url": "drivers",
			    },
			    "sAjaxDataProp":"",
		        "columns": [
		            { "data": "name" },
		            { "data": "licenseNumber" },
		            { "data": "phoneNumber" },
		            { "data": "yearsOfExperience" },
		            { "data": "id" }
		        ]
	    });
	});
</script>