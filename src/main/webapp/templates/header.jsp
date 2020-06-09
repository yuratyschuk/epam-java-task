<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>

<nav class="navbar navbar-dark bg-dark">
    <a class="nav-link" href="${pageContext.servletContext.contextPath}/worker?action=workerList">Workers
        info</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/trip?action=tripSearch">Search trip</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/train?action=trainList">Train list</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/position?action=positionList">Positions</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/ticket?action=ticketList">Ticket List</a>

    <c:choose>
    <c:when test="${sessionScope.user == null}">

        <a class="nav-link" href="${pageContext.servletContext.contextPath}/user?action=userRegister">Registration</a>

        <a class="nav-link" href="${pageContext.servletContext.contextPath}/user?action=userLogin">Login</a>
    </c:when>
        <c:otherwise>
           <a class="nav-link" href="${pageContext.servletContext.contextPath}/user?action=userPage">User</a>
        </c:otherwise>
    </c:choose>
</nav>
${sessionScope}
