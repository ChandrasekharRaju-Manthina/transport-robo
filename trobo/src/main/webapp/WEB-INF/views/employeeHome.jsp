<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			Employee details
		</h3>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Enter details</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="employeeForm" name="employeeForm" method="POST" action="employees">
                		<input type="hidden" name="status" value="A">
                		<input type="hidden" name="mangerId" value="0">
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Name</label>
                                <input class="form-control" name="name" required="required" placeholder="Enter name">
                                <p class="help-block error-msg" id="name-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Password</label>
                                <input class="form-control" type="password" name="password" required="required" placeholder="Enter password">
                                <p class="help-block error-msg" id="password-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Gender</label>
                               <select class="form-control" name="sex" required="required">
                                	<option value="">Please select</option>
                                	<option value="F">Female</option>
                                	<option value="M">Male</option>
                                </select>
                                <p class="help-block error-msg" id="sex-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Role</label>
                                <select class="form-control" name="role" required="required">
                                	<option value="">Please select</option>
									<option value="employee">employee</option>
									<option value="manager">manager</option>
                                	<option value="admin">admin</option>
								</select>
                                <p class="help-block error-msg" id="driverId-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	<div class="row">
                		<div class="col-lg-12 text-center">
                			<button type="submit" style="width: 80px;" class="btn btn-primary">Register</button>
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

<script>
	$(document).ready(function() {
		
	   
	});
</script>