<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
	
	   <c:if test="${not empty exception}">
            <div class="container">
                <div class="alert alert-danger">
                    <strong>Danger!</strong> <c:out value="${exception}" />
                </div>
            </div>
        </c:if>
    
    
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer?action=newComputer" method="POST" id="addComputerForm">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" name="computerName"
									id="computerName" placeholder="Computer name">
							</div>

							<div class="form-group">
								<label for="introducedDate">Introduced date</label> <input
									type="date" class="form-control" name="introducedDate"
									id="introducedDate" placeholder="Introduced date">
							</div>
							
							<div class="form-group">
								<label for="discontinuedDate">Discontinued date</label> <input
									type="date" class="form-control" name="discontinuedDate"
									id="discontinuedDate" placeholder="Discontinued date">
							</div>

							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<!-- <option value="0">--</option> -->
									<c:forEach items="${companyList}" var="company">
										<option value="<c:out value="${company.getCompanyId()}"/>">
											<c:out value="${company.getCompanyName()}" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						
						<div class="actions pull-right">
							<input type="submit" id="submitForm" value="Add" class="btn btn-primary"> or 
							<a href="${pageContext.request.contextPath}/dashboard"	class="btn btn-default">Cancel</a>
						</div>
						
					</form>
				</div>
			</div>
		</div>
	</section>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/addComputer.js"></script>
</body>
</html>

