 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            <!-- /input-group -->
                        </li>
                        <% if (request.isUserInRole("admin")) { %>
	                        <li>
	                            <a id="home-tour" href="<c:url value="/home" />"><i class="fa fa-fw fa-dashboard"></i> Home</a>
	                        </li>
	                        <li>
	                            <a id="address-tour" href="<c:url value="/addressPage" />"><i class="fa fa-fw fa-home"></i> Address</a>
	                        </li>
	                        <li>
	                            <a id="vehicle-tour" href="<c:url value="/vehiclesPage" />"><i class="fa fa-fw  fa-truck"></i> Vehicles</a>
	                        </li>
	                        <li>
	                            <a id="driver-tour" href="<c:url value="/driversPage" />"><i class="fa fa-fw fa-male"></i> Drivers</a>
	                        </li>
	                        <li>
	                            <a id="shift-tour" href="<c:url value="/shiftPage" />"><i class="glyphicon glyphicon-time"></i> Shift</a>
	                        </li>
	                        <li id="tripsheet-tour">
	                            <a href="#"><i class="fa fa-fw fa-list-alt"></i> Trip sheet<span class="fa arrow"></span></a>
	                            <ul class="nav nav-second-level">
	                                <li>
	                                    <a href="<c:url value="/tripSheetPage" />">Generate</a>
	                                </li>
	                                <li>
	                                    <a href="<c:url value="/viewTripSheet" />">View</a>
	                                </li>
	                            </ul>
	                            <!-- /.nav-second-level -->
	                        </li>
	                        <li>
	                            <a id="address-tour" href="<c:url value="/updateAddressPage" />"><i class="fa fa-fw fa-home"></i> Update Address</a>
	                        </li>
	                        <li id="viewcab-tour">
	                            <a href="<c:url value="/viewCabPage" />"><i class="glyphicon glyphicon-map-marker"></i> View Cab</a>
	                        </li>
	                        <li id="transport-req-tour">
	                            <a href="#"><i class="fa fa-fw fa-car"></i> Transport request<span class="fa arrow"></span></a>
	                            <ul class="nav nav-second-level">
	                                <li>
	                                    <a href="<c:url value="/transportReqPage" />">Transport request</a>
	                                </li>
	                               <!--  <li>
	                                    <a href="#">Adhoc request</a>
	                                </li>
	                                <li>
	                                    <a href="#">Cancel request</a>
	                                </li>  -->
	                            </ul>
	                            <!-- /.nav-second-level -->
	                        </li>
	                        <li>
	                            <a href="#"><i class="glyphicon glyphicon-time"></i> Shift change request</a>
	                        </li>	                        
	                        <li>
	                            <a href="#"><i class="fa fa-fw fa-money"></i> Invoice generation</a>
	                        </li>
	                        <li>
	                            <a href="#"><i class="glyphicon glyphicon-tasks"></i>Reports</a>
	                        </li>
                        <% } else if (request.isUserInRole("manager")) { %>
	                        <li>
	                            <a id="home-tour" href="<c:url value="/home" />"><i class="fa fa-fw fa-dashboard"></i> Home</a>
	                        </li>
	                        <li>
	                            <a id="approval-tour" href="<c:url value="#" />"><i class="fa fa-fw fa-check-square-o"></i> My Approvals</a>
	                        </li>
	                       <li>
	                            <a id="address-tour" href="<c:url value="/updateAddressPage" />"><i class="fa fa-fw fa-home"></i> Update Address</a>
	                        </li>
	                        <li>
	                            <a id="viewcab-tour" href="<c:url value="/viewCabPage" />"><i class="glyphicon glyphicon-map-marker"></i> View Cab</a>
	                        </li>
	                        <li id="transport-req-tour">
	                            <a href="#"><i class="fa fa-fw fa-car"></i> Transport request<span class="fa arrow"></span></a>
	                            <ul class="nav nav-second-level">
	                                <li>
	                                    <a href="<c:url value="/transportReqPage" />">Transport request</a>
	                                </li>
	                               <!--  <li>
	                                    <a href="#">Adhoc request</a>
	                                </li>
	                                <li>
	                                    <a href="#">Cancel request</a>
	                                </li>  -->
	                            </ul>
	                            <!-- /.nav-second-level -->
	                        </li>
	                        <li>
	                            <a href="#"><i class="glyphicon glyphicon-time"></i> Shift change request</a>
	                        </li>
                          <% } else if (request.isUserInRole("employee")) { %>
	                        <li>
	                            <a id="home-tour" href="<c:url value="/home" />"><i class="fa fa-fw fa-dashboard"></i> Home</a>
	                        </li>
	                        <li>
	                            <a id="address-tour" href="<c:url value="/updateAddressPage" />"><i class="fa fa-fw fa-home"></i> Update Address</a>
	                        </li>
	                        <li>
	                            <a id="viewcab-tour" href="<c:url value="/viewCabPage" />"><i class="glyphicon glyphicon-map-marker"></i> View Cab</a>
	                        </li>
	                        <li id="transport-req-tour">
	                            <a href="#"><i class="fa fa-fw fa-car"></i> Transport request<span class="fa arrow"></span></a>
	                            <ul class="nav nav-second-level">
	                                <li>
	                                    <a href="<c:url value="/transportReqPage" />">Transport request</a>
	                                </li>
	                               <!--  <li>
	                                    <a href="#">Adhoc request</a>
	                                </li>
	                                <li>
	                                    <a href="#">Cancel request</a>
	                                </li>  -->
	                            </ul>
	                            <!-- /.nav-second-level -->
	                        </li>
	                        <li>
	                            <a href="#"><i class="glyphicon glyphicon-time"></i> Shift change request</a>
	                        </li>
                        <% } %>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        
<script type="text/javascript">

<% if (request.isUserInRole("admin")) { %>
var steps =  [
  {
    element: "#alerts-tour",
    title: "Welcome to Transport robo!",
    content: "Use this option to see the alerts.",
    placement: "left",
  },
  {
    element: "#home-tour",
    title: "Transport robo Dashboard",
    content: "Dashboard shows request and approvals count."
  },
  {
    element: "#address-tour",
    title: "Transport addresses",
    content: "Maintain addresses where company provides transportation."
  },
  {
    element: "#vehicle-tour",
    title: "Trasnport vehicles",
    content: "Manages all the transport vehicles. you can assign driver to each vehicle."
  },
  {
    element: "#driver-tour",
    title: "Transport drivers",
    content: "Manages transport vehicle driver details."
  },
  {
    element: "#shift-tour",
    title: "Transport shifts",
    content: "Maintains all the available shifts in the company."
  },
  {
    element: "#tripsheet-tour",
    title: "Transport tripsheet",
    content: "Generates tripsheet with vehicles routes and employees details."
  },
  {
    element: "#viewcab-tour",
    title: "Transport cab",
    content: "You can find your shuttle and its driver details."
  },
  {
    element: "#transport-req-tour",
    title: "Transport request",
    content: "You can submit both transport and adhoc request."
  }
];
<% } else if (request.isUserInRole("manager")) { %>
var steps =  [
  {
    element: "#alerts-tour",
    title: "Welcome to Transport robo!",
    content: "Use this option to see the alerts.",
    placement: "left",
  },
  {
    element: "#home-tour",
    title: "Transport robo Dashboard",
    content: "Dashboard shows request and approvals count."
  },
  {
   	element: "#approval-tour",
    title: "Trasnport request approvals",
    content: "Manager approves or rejects transport requests."
  },
  {
   	element: "#address-tour",
    title: "Transport address",
    content: "Add/Updates transport address or pick up point.."
  },
  {
    element: "#viewcab-tour",
    title: "Transport cab",
    content: "You can find your shuttle and its driver details."
  },
  {
    element: "#transport-req-tour",
    title: "Transport request",
    content: "You can submit both transport and adhoc request."
  }
];
 <% } else if (request.isUserInRole("employee")) { %>
 var steps =  [
  {
    element: "#alerts-tour",
    title: "Welcome to Transport robo!",
    content: "Use this option to see the alerts.",
    placement: "left",
  },
  {
    element: "#home-tour",
    title: "Transport robo Dashboard",
    content: "Dashboard shows request and approvals count."
  },
  {
   	element: "#address-tour",
    title: "Transport address",
    content: "Add/Updates transport address or pick up point.."
  },
  {
    element: "#viewcab-tour",
    title: "Transport cab",
    content: "You can find your shuttle and its driver details."
  },
  {
    element: "#transport-req-tour",
    title: "Transport request",
    content: "You can submit both transport and adhoc request."
  }
];
 <% } %>
// Instance the tour
var tour = new Tour({
  steps: steps
});

// Initialize the tour
tour.init();

// Start the tour
tour.start();
</script>   
        