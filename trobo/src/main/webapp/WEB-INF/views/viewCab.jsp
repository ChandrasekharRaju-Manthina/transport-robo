<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<s:message code="viewCab.header" text="Welcome" />
		</h3>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">View cab</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="viewCabForm" name="viewCabForm" method="POST" action="tripSheet/view">
                	 <div class="row">
                		<div class="col-lg-4">
                			<div class="form-group">
                                <label>Date</label>
                                <input class="form-control" id="tripSheetDate" name="date" type="date" required="required">
                                <p class="help-block error-msg" id="date-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-4">
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
                		<div class="col-lg-4">
                			<div class="form-group">
                                <label>Type</label>
                                <select class="form-control" name="drop" id="drop" required="required">
                                	<option value="">Select</option>
                                	<option value="true">Drop</option>
                                	<option value="false">Pickup</option>
                                </select>
                                <p class="help-block error-msg" id="drop-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>            	
                	
                	
                	<div class="row">
                		<div class="col-lg-12 text-center">                			
                			<button type="submit" id="viewCab" style="width: 80px;" class="btn btn-primary">View</button>
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

<div class="row" id="message" style="display: none;">
	<div class="col-lg-12">           
		<div class="alert alert-warning">
     		<p id="successMsgTxt">Cab details were not found.</p> 
     		
		</div>
	</div>
</div>

<div class="row" id="vehicleDetails" style="display: none;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Cab details</strong>
            </div>
            <div class="panel-body">
                	 <input type="hidden" name="id">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Vehicle number</label>
                                <p class="form-control-static" id="vehicleNumber"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Driver name</label>
                                <p class="form-control-static"  id="driverName"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Driver number</label>
                                 <p class="form-control-static" id="driverNumber"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Track</label>
                                 <a href="#"><p class="form-control-static" id="track"></p> </a>
                            </div>
                		</div>
                	</div>
                <!-- /.row (nested) -->
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
