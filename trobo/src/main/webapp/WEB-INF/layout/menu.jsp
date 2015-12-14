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
	                            <a href="<c:url value="/home" />"><i class="fa fa-fw fa-dashboard"></i> Home</a>
	                        </li>
	                        <li>
	                            <a href="<c:url value="/addressPage" />"><i class="fa fa-fw fa-dashboard"></i> Address</a>
	                        </li>
	                        <li>
	                            <a href="<c:url value="/vehiclesPage" />"><i class="fa fa-fw fa-dashboard"></i> Vehicles</a>
	                        </li>
	                        <li>
	                            <a href="<c:url value="/driversPage" />"><i class="fa fa-fw fa-dashboard"></i> Drivers</a>
	                        </li>
                        <% } else if (request.isUserInRole("manager")) { %>
	                        <li>
	                            <a href="<c:url value="/home" />"><i class="fa fa-fw fa-dashboard"></i> Home</a>
	                        </li>
	                        <li>
	                            <a href="<c:url value="#" />"><i class="fa fa-fw fa-dashboard"></i> My Approvals</a>
	                        </li>
	                        <li>
	                            <a href="<c:url value="#" />"><i class="fa fa-fw fa-dashboard"></i> My profile</a>
	                        </li>
                          <% } else if (request.isUserInRole("employee")) { %>
	                        <li>
	                            <a href="<c:url value="/home" />"><i class="fa fa-fw fa-dashboard"></i> Home</a>
	                        </li>
	                        <li>
	                            <a href="<c:url value="#" />"><i class="fa fa-fw fa-dashboard"></i> My profile</a>
	                        </li>
                        <% } %>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        

        