<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			Update address details
		</h3>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Update address</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="updateAddressForm" method="POST" 
                		action="addresses/employee" data-success-msg="Address has been updated successfully.">
                	 <input type="hidden" name="id">
                	 <div class="row">
                		<div class="col-lg-12">
                			<div class="form-group">
                                <label>Request type</label>
                                 <select class="form-control" id="addressId" name="addressId" required="required">
                                	<option value="">Select</option>
									<c:forEach var="address" items="${addresses}">
									    <option ${address.id == addressId ? 'selected=selected' : ''} value="${address.id}">${address.addressLine}-${address.city}-${address.state}-${address.zip}-${address.country}</option>
									</c:forEach>
								</select>
                                <p class="help-block error-msg" id="requestType-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                
                	<div class="row">
                		<div class="col-lg-12 text-center">
                			<button type="submit" style="width: 80px;" class="btn btn-primary">Update</button>
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