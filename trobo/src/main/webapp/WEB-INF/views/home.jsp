<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false"%>
<html>
<head>
<title>Spittr</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/style.css" />">
</head>
<body>
	 
	 <div class="row">
	 	<div class="col-lg-12">
			<h1 class="page-header"><s:message code="spitter.welcome" text="Welcome" /></h1>
		</div>
	</div>
	
	<div class="row">
	 	<div class="col-lg-2">
	 		<a href="<c:url value="/spittles" />">Spittles</a>
	 	</div>
	 	<div class="col-lg-2">
	 		<a href="<c:url value="/spitter/register" />">Register</a>
	 	</div>
	</div>	
</body>
</html>