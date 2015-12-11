<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    
    <!-- MetisMenu CSS -->
    <link href="<c:url value="/resources/css/metisMenu.min.css" />" rel="stylesheet">
    
     <!-- Timeline CSS -->
    <link href="<c:url value="/resources/css/timeline.css" />" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value="/resources/css/sb-admin-2.css" />" rel="stylesheet">
    
     <!-- DataTables CSS -->
    <link href="<c:url value="/resources/datatables/media/css/dataTables.bootstrap.css" />" rel="stylesheet">

    <!-- DataTables Responsive CSS 
    <link href="<c:url value="/resources/datatables-responsive/css/dataTables.responsive.css" />" rel="stylesheet"> -->

    <!-- Custom Fonts -->
    <link href="<c:url value="/resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
     <!-- jQuery -->
    <script src="<c:url value="/resources/js/jquery.js" />"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    
     <!-- Metis Menu Plugin JavaScript -->
    <script src="<c:url value="/resources/js/metisMenu.min.js" />"></script>
    
     <!-- Custom Theme JavaScript -->
    <script src="<c:url value="/resources/js/sb-admin-2.js" />"></script>
    
     <!-- DataTables JavaScript -->
    <script src="<c:url value="/resources/datatables/media/js/jquery.dataTables.min.js" />"></script>
    <script src="<c:url value="/resources/datatables/media/js/dataTables.bootstrap.min.js" />"></script>
   

</head>

<body>
	<div id="wrapper">
	
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<t:insertAttribute name="header" />
		  	<t:insertAttribute name="menu" />
	  	</nav>
	  	
	  	<div id="page-wrapper">
	  			<t:insertAttribute name="body" />
	  	</div>
	  	 <!-- /#page-wrapper -->
	  	
	</div>	
	 <!-- /#wrapper -->
	 
	 
    
</body>
</html>