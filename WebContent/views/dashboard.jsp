<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="pagination"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen"> 
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>

    <section id="main">
		<div class="container">
			<h1 id="homeTitle">${requestScope.nbComputer} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET" class="form-inline">
					 <input type=hidden name="sortnew" value="${requestScope.sortnew}" />
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${requestScope.search}"/> 
						<!-- If one day I will want to search by computer or by company -->
						<!-- <select class="form-control" name="by">
						    <option value='--' <c:if test="${by == '--'}"> selected</c:if>> --</option>
                            <option value='computer' <c:if test="${by == 'computer'}"> selected</c:if>> Computers</option>
                            <option value='computer' <c:if test="${by == 'computer'}"> selected</c:if>> Companies</option>
                        </select>  -->
						<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="dashboard" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;"> - 
                                <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> 
                                <i class="fa fa-trash-o fa-lg"></i> 
                                </a>
                            </span>
                        </th>
                        <!-- 
                        <th>Computer name</th>
                        <th>Introduced date</th> -->
                        <!-- Table header for Discontinued Date -->
                        <!--<th>Discontinued date</th>-->
                        <!-- Table header for Company -->
                        <!-- <th>Company</th> -->
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=name&search=${requestScope.search}">Computer name</a></th>
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=introduced&search=${requestScope.search}">Introduced date</a></th>
                        <!-- Table header for Discontinued Date -->
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=discontinued&search=${requestScope.search}">Discontinued date</a></th>
                        <!-- Table header for Company -->
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=companyName&search=${requestScope.search}">Company</a></th>
					   </tr>
    			</thead>
                
                <!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${requestScope.computerList}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.getComputerId()}"></td>    
							<td><a href='editComputer?computerId=<c:out value="${computer.computerId}"/>'
                                   onclick=""><c:out value="${computer.computerName}" />
                                </a>
                             </td>
							<td><c:out value="${computer.getIntroducedDate()}" /></td>
							<td><c:out value="${computer.getDiscontinuedDate()}" /></td>
							<td><c:out value="${computer.getCompanyId()}" /></td>
						</tr>						
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
            <c:url var="searchUri" value="/dashboard?page=##&submit=${requestScope.submit}&sort=${requestScope.sort}&search=${requestScope.search}" />
			<pagination:display maxLinks="10" currentPage="${currentPage}" maxPages="${maxPage}" uri="${searchUri}" />
			<div class="btn-group btn-group-sm pull-right" role="group">
				<form action="dashboard" method="GET">
					<input type="submit" name="submit" value="10" class="btn btn-default"> 
					<input type="submit" name="submit" value="50" class="btn btn-default"> 
					<input type="submit" name="submit" value="100" class="btn btn-default">
				</form>
			</div>
		</div>
	</footer>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>