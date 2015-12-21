<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Welcome to Transport robo!
		</h1>
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