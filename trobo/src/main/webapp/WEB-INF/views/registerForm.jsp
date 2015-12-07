<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false"%>

 	<div class="row">
	 	<div class="col-lg-12">
			<h1 class="page-header">Register</h1>
		</div>
	</div>
	
	<div class="row">
		  <div class="col-lg-6">
		  		<sf:form method="POST" commandName="spitter">
		  		
		  			<div class="row">
		  				<div class="col-lg-6">
		  					<sf:errors path="*" element="div" cssClass="errors" />
		  				</div>
		  			</div>
		  			
		  			 <div class="form-group">
                         <label>First Name:</label>
                         <sf:input path="firstName" cssClass="form-control"/>
                     </div>
                     
                      <div class="form-group">
                         <label>Last Name:</label>
                         <sf:input path="lastName" cssClass="form-control"/>
                     </div>
                     
                     <div class="form-group">
                         <label>Email:</label>
                         <sf:input path="email" cssClass="form-control"/>
                     </div>
                     
                     <div class="form-group">
                         <label>Username:</label>
                         <sf:input path="username" cssClass="form-control"/>
                     </div>
                     
                     <div class="form-group">
                         <label>Password:</label>
                         <sf:input path="password" cssClass="form-control"/>
                     </div>
                     
                      <button type="submit" class="btn btn-default">Register</button>
		  		
		  		</sf:form>
		  	
		  </div>
	
	</div>
	
	