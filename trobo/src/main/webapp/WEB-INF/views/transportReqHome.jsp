<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			Transport request details
		</h3>
		<ol class="breadcrumb" id="addLink" style="display: none;">
            <li>
                <i class="fa fa-male"></i>  <a href="#" name="addMenu" onclick="resetToAdd('transportReqForm')">Request transport</a>
            </li>
        </ol>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Transport request</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="transportReqForm" method="POST" action="transportReqs" data-success-msg="Transport request has been submitted successfully.">
                	 <input type="hidden" name="id">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Request type</label>
                                <select class="form-control" name="requestType" required="required">
                                	<option value="">Please select</option>
                                	<option value="T">Transport request</option>
                                	<option value="A">Adhoc request</option>
								</select>
                                <p class="help-block error-msg" id="requestType-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                 <label>Shift</label>
                               <select class="form-control" id="shiftId" name="shiftId" required="required">
                                	<option value="">Select</option>
									<c:forEach var="shift" items="${shifts}">
									    <option value="${shift.id}">${shift.startTime}-${shift.endTime}</option>
									</c:forEach>
								</select>
                                <p class="help-block error-msg" id="shiftId-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Start date</label>
                                <input class="form-control" type="date" name="startDate" id="startDate" required="required">
                                <p class="help-block error-msg" id="startDate-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>End date</label>
                                <input class="form-control" type="date" name="endDate" id="endDate" required="required">
                                <p class="help-block error-msg" id="endDate-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	<div class="row">
                		<div class="col-lg-12 text-center">
                			<button type="submit" style="width: 80px;" class="btn btn-primary">Submit</button>
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
			                <th>Start date</th>
			                <th>End date</th>
			                <th>Request Type</th>
			                <th>Shift id</th>
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
	
		var dtToday = new Date();
		dtToday.setDate(dtToday.getDate() + 1);
	
	    var month = dtToday.getMonth() + 1;
	    var day = dtToday.getDate();
	    var year = dtToday.getFullYear();
	
	    if(month < 10)
	        month = '0' + month.toString();
	    if(day < 10)
	        day = '0' + day.toString();
	
	    var maxDate = year + '-' + month + '-' + day;    
	    $("#startDate").attr("min", maxDate);
	    $("#endDate").attr("min", maxDate);
		
	    $("#data-table").dataTable({
	    		"columnDefs": [ {
				    "targets": 4,
				    "render": function ( data, type, full, meta ) {
					      return '<a title="update" class="update" href="#" data-id="' + data +'"><span class="glyphicon glyphicon-edit update">Edit&nbsp;</span></a>' + '<a title="delete" class="delete" href="#" data-url="transportReqs" data-success-msg="Transport request has been deleted successfully." data-id="' + data +'"><span class="glyphicon glyphicon-remove-sign delete">Delete</span></a>';
				    }
				}],
	            "ajax": {
			       "url": "transportReqs/employee",
			    },
			    "sAjaxDataProp":"",
		        "columns": [
		            { "data": "startDate" },
		            { "data": "endDate" },
		            { "data": "requestType" },
		            { "data": "shiftId" },
		            { "data": "id" }
		        ]
	    });
	});
	
	
</script>