<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


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
				<div class="label label-default pull-right">
					<c:out value="${requestScope.computer.id}" />
				</div>
				<h1>Edit Computer</h1>

				<form action="editComputer" method="POST">
					<input type="hidden" name="computerId"
						value='<c:out value="${requestScope.computer.id}"/>' id="id" />
					<fieldset>
						<div class="form-group">
							<label for="computerName"><c:out value="Computer name"></c:out></label>
							<input type="text" class="form-control" name="computerName"
								id="computerName"
								value='<c:out value="${requestScope.computer.name}"/>'>
						</div>
						<div class="form-group">
							<fmt:parseDate
								value="${requestScope.computer.introduced}"
								pattern="yyyy-MM-dd" var="parsedIntroDate" type="date" />
							<fmt:formatDate value="${parsedIntroDate}" var="intro"
								pattern="yyyy-MM-dd" />
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" name="introduced"
								id="introduced" value="<c:out value="${intro}"/>">

						</div>
						<div class="form-group">
							<fmt:parseDate
								value="${requestScope.computer.discontinued}"
								pattern="yyyy-MM-dd" var="parsedDiscoDate" type="date" />
							<fmt:formatDate value="${parsedDiscoDate}" var="disco"
								pattern="yyyy-MM-dd" />
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" name="discontinued"
								id="discontinued" value="<c:out value="${disco}"/>">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select
								class="form-control" name="companyId" id="companyId">
								<option value="0">--</option>
								<c:forEach var="company" items="${requestScope.companies}">

									<option value="<c:out value="${company.id}"/>"
										<c:if test="${requestScope.computer.company.id==company.id}">selected</c:if>><c:out value="${company.name}"/></option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" id="submit" value="Edit"
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