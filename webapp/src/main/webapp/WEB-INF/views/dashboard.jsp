<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="pagination"%>
<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%> 

<!-- Spring translation references -->
<springTags:message code="computer.tab" var="tabName"></springTags:message>
<springTags:message code="computer.title" var="title"></springTags:message>
<springTags:message code="computer.language_en" var="languageEn"></springTags:message>
<springTags:message code="computer.language_fr" var="languageFr"></springTags:message>
<springTags:message code="computer.computersFound" var="computersFound"></springTags:message>
<springTags:message code="computer.searchName" var="searchName"></springTags:message>
<springTags:message code="computer.filterByName" var="filterByName"></springTags:message>
<springTags:message code="computer.addComputer" var="addComputer"></springTags:message>
<springTags:message code="computer.editComputer" var="editComputer"></springTags:message>
<springTags:message code="computer.edit" var="edit"></springTags:message>
<springTags:message code="computer.name" var="name"></springTags:message>
<springTags:message code="computer.introducedDate" var="introducedDate"></springTags:message>
<springTags:message code="computer.discontinuedDate" var="discontinuedDate"></springTags:message>
<springTags:message code="computer.company" var="company"></springTags:message>
<springTags:message code="computer.add" var="add"></springTags:message>
<springTags:message code="computer.or" var="or"></springTags:message>
<springTags:message code="computer.cancel" var="cancel"></springTags:message>
 
<!DOCTYPE html>
<html>
<head>
<title><c:out value="${tabName}"></c:out></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" media="screen"> 

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><c:out value="${title}"></c:out></a>
		</div>
	</header>

	<span style="float: right"> 
		<a href="?language=en">
			<c:out value="${languageEn}"></c:out>
		</a>|
		<a href="?language=fr">
			<c:out value="${languageFr}"></c:out>
		</a>
	</span>
	
    <section id="main">
		<div class="container">
			<h1 id="homeTitle">${requestScope.nbComputer} <c:out value="${computersFound}"></c:out></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="<c:out value="${searchName}"></c:out>" value="${requestScope.search}"/> 
						<input type="submit" id="searchsubmit" value="<c:out value="${filterByName}"></c:out>" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><c:out value="${addComputer}"></c:out></a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><c:out value="${editComputer}"></c:out></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
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
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=name&search=${requestScope.search}"><c:out value="${name}"></c:out></a></th>
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=introduced&search=${requestScope.search}"><c:out value="${introducedDate}"></c:out></a></th>
                        <!-- Table header for Discontinued Date -->
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=discontinued&search=${requestScope.search}"><c:out value="${discontinuedDate}"></c:out></a></th>
                        <!-- Table header for Company -->
                        <th><a href="${pageContext.request.contextPath}/dashboard?page=${currentPage}&submit=${submit}&sort=companyName&search=${requestScope.search}"><c:out value="${company}"></c:out></a></th>
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
							<td><c:out value="${computer.getCompanyName()}" /></td>
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

<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>

</body>
</html>