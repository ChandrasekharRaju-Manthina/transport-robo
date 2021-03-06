<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<s:message code="shift.header" text="Welcome" />
		</h3>
		<ol class="breadcrumb" id="addLink" style="display: none;">
            <li>
                <i class="glyphicon glyphicon-time"></i>  <a href="#" name="addMenu" onclick="resetToAdd('driverForm')">Add shift</a>
            </li>
        </ol>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Add Shift</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="shiftForm" method="POST" action="shifts" data-success-msg="Shift has been added successfully.">
                	 <input type="hidden" name="id">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Start time</label>
                                <input class="form-control" name="startTime" type="time" required="required">
                                <p class="help-block error-msg" id="name-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>End time</label>
                                <input class="form-control" type="time" name="endTime" >
                                <p class="help-block error-msg" id="licenseNumber-error" style="display: none;"></p>
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
               <strong>Shift details</strong>
            </div>
            <div class="panel-body">
            	<table id="data-table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
			        <thead>
			            <tr>
			                <th>Start time</th>
			                <th>End time</th>
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
				    "targets": 2,
				    "render": function ( data, type, full, meta ) {
					      return '<a title="update" class="update" href="#" data-id="' + data +'"><span class="glyphicon glyphicon-edit update">Edit&nbsp;</span></a>' + '<a title="delete" class="delete" href="#" data-url="shifts" data-success-msg="Shift has been deleted successfully." data-id="' + data +'"><span class="glyphicon glyphicon-remove-sign delete">Delete</span></a>';
				    }
				}],
	            "ajax": {
			       "url": "shifts",
			    },
			    "sAjaxDataProp":"",
		        "columns": [
		            { "data": "startTime" },
		            { "data": "endTime" },
		            { "data": "id" }
		        ]
	    });
	});
</script>