<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
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
        <div class="panel panel-default">
            <div class="panel-heading">
               <strong id="header">Add Address</strong>
            </div>
            <div class="panel-body">
                
                	<form role="form" id="form" name="addressForm" method="POST" action="addresses" data-success-msg="Address has been added successfully.">
                	 <input type="hidden" name="id">
                	 <div class="row">
                	 	<div class="col-lg-12">
                			<div class="form-group">
                                <label>Address line</label>
                                <input class="form-control" name="addressLine" placeholder="Enter address line">
                                <p class="help-block error-msg" id="addressLine-error" style="display: none;"></p>
                            </div>
                		</div>		
                	 </div>
                	 <div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>City</label>
                                <input class="form-control" name="city" placeholder="Enter city">
                                <p class="help-block error-msg" id="city-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>State</label>
                                <input class="form-control" name="state" placeholder="Enter state">
                                <p class="help-block error-msg" id="state-error" style="display: none;"></p>
                            </div>
                		</div>
                	</div>
                	
                	<div class="row">
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Zip</label>
                                <input class="form-control" name="zip" placeholder="Enter zip">
                                <p class="help-block error-msg" id="zip-error" style="display: none;"></p>
                            </div>
                		</div>
                		<div class="col-lg-6">
                			<div class="form-group">
                                <label>Country</label>
                                <input class="form-control" name="country" placeholder="Enter country">
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
				    "targets": 5,
				    "render": function ( data, type, full, meta ) {
					      return '<a title="update" class="update" href="#" data-id="' + data +'"><span class="glyphicon glyphicon-edit update">Edit&nbsp;</span></a>' + '<a title="delete" class="delete" href="#" data-url="addresses" data-success-msg="Address has been deleted successfully." data-id="' + data +'"><span class="glyphicon glyphicon-remove-sign delete">Delete</span></a>';
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
		            { "data": "id" }
		        ]
	    });
	});
</script>