<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
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
                    <div class="label label-default pull-right">
                        <c:out value="${requestScope.computerToEdit.computerId}" />
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" name="computerId" id="computerId" value="${requestScope.computerToEdit.computerId}"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="Computer name" value="${requestScope.computerToEdit.getComputerName()}">
                            </div>
                            <div class="form-group">
                                <label for="introducedDate">Introduced date</label>
                                <input type="date" class="form-control" name="introducedDate" id="introducedDate" placeholder="Introduced date" value="${requestScope.computerToEdit.getIntroducedDate()}">
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinuedDate" id="discontinuedDate" placeholder="Discontinued date" value="${requestScope.computerToEdit.getDiscontinuedDate()}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" name="companyId" id="companyId" >
                                    <option value="${computerToEdit.getCompanyId()}">
                                        <c:out value="${companyOfTheEditedComputer.getCompanyName()}" />
                                    </option>
                                    <c:forEach items="${companyList}" var="company">
                                        <option value="<c:out value="${company.getCompanyId()}"/>">
                                            <c:out value="${company.getCompanyName()}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>                                           
                                    
                        </fieldset>        
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>