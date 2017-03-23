<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">

	<c:if test="${not empty exception}">
		<div class="container">
			<div class="alert alert-danger">
				<c:out value="${exception}" />
			</div>
		</div>
	</c:if>

	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form action="addComputer" method="POST">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" class="form-control" name="computerName"
								id="computerName" placeholder="Computer name">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" name="introduced"
								id="introduced">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" name="discontinued"
								id="discontinued">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select
								class="form-control" id="companyId" name="companyId">
								<option value="0">--</option>
								<c:forEach var="company" items="${requestScope.companies}">
									<option value='<c:out value="${company.id}"/>'><c:out
											value="${company.name}" /></option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" id="submit"
							class="btn btn-primary"> or <a href="dashboard.html"
							class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/validate.js"></script>
</body>
</html>