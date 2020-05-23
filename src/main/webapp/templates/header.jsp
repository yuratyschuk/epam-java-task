<head>
    <title>Header</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>

<nav class="navbar navbar-dark bg-dark">
    <a class="nav-link" href="${pageContext.servletContext.contextPath}/worker?action=listWorkers">Workers
        info</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/trip?action=search">Search trip</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/train?action=listTrain">Train list</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/position?action=positionList">Positions</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/ticket?action=ticketList">Ticket List</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/user?action=register">Registration</a>

    <a class="nav-link" href="${pageContext.servletContext.contextPath}/user?action=login">Login</a>

</nav>
