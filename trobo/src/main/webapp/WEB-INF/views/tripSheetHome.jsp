<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<s:message code="tripSheet.header" text="Welcome" />
		</h3>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Generate trip sheet</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="tripSheetForm" method="POST" action="tripSheet">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Date</label>
                                <input class="form-control" name="date" type="date" placeholder="Enter date" required="required">
                                <p class="help-block error-msg" id="date-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Shift</label>
                               <select class="form-control" name="shiftId" required="required">
                                	<option value="">Please select</option>
									<c:forEach var="shift" items="${shifts}">
									    <option value="${shift.id}">${shift.startTime}-${shift.endTime}</option>
									</c:forEach>
								</select>
                                <p class="help-block error-msg" id="shiftId-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-12 text-center">
                			<div class="form-group">
                                <label>Vehicles</label>
                               <select class="form-control" name="vehicleCapcities" required="required" multiple="multiple">
									<c:forEach var="vehicle" items="${vehicles}">
									    <option value="${vehicle.id}">${vehicle.vehicleNumber}</option>
									</c:forEach>
								</select>
                                <p class="help-block error-msg" id="vehicleIds-error" style="display: none;"></p>
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