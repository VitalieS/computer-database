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
    <fmt:formatNumber var="numberPageMax"
        value="${requestScope.totalNumberComputers/requestScope.page.numberPerPage}"
        maxFractionDigits="0" />
    <c:out value="${numberPageMax}"></c:out>

    <div class="container">
        <h1 id="homeTitle">
            <c:out value="${requestScope.totalNumberComputers} Computers found" />
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">

                    <input type="search" id="searchbox" name="search"
                        class="form-control" placeholder="Search name" /> <input
                        type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="addComputer">Add
                    Computer</a> <a class="btn btn-default" id="editComputer" href="#"
                    onclick="$.fn.toggleEditMode();">Edit</a>
            </div>
        </div>
    </div>

    <form id="deleteForm"
        action="${pageContext.request.contextPath}/deleteComputer"
        method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <!-- Variable declarations for passing labels as parameters -->
                    <!-- Table header for Computer Name -->

                    <th class="editMode" style="width: 60px; height: 22px;"><input
                        type="checkbox" id="selectall" /> <span
                        style="vertical-align: top;"> - <a href="#"
                            id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
                                class="fa fa-trash-o fa-lg"></i>
                        </a>
                    </span></th>
                    <th>Computer name</th>
                    <th>Introduced date</th>
                    <!-- Table header for Discontinued Date -->
                    <th>Discontinued date</th>
                    <!-- Table header for Company -->
                    <th>Company</th>

                </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
                <c:forEach var="computer" items="${requestScope.listComputers}">
                    <tr>
                        <td class="editMode"><input type="checkbox" name="cb"
                            class="cb" value='<c:out value="${computer.id}"/>'></td>
                        <td><a
                            href='editComputer?computerId=<c:out value="${computer.id}"/>'
                            onclick=""><c:out value="${computer.name}" /></a></td>
                        <td><c:out value="${computer.introduced}" /></td>
                        <td><c:out value="${computer.discontinued}" /></td>
                        <td><c:out value="${computer.company.name}" /></td>

                    </tr>
                </c:forEach>
            </tbody>

        </table>
    </div>

</section>


<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>