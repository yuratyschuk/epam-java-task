<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">

<head>
    <jsp:include page="../../templates/header.jsp"></jsp:include>
    <title>Update Worker</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>

<body>


<div id="container">
    <h3>Update Worker</h3>

    <form action="worker" method="post">


        <input type="hidden" name="workerId" value="${worker.id}"/>


        <label>First name:</label>
        <input type="text" name="firstName" class="form-control"
               value="${worker.firstName}" required/>
        <br>
        <label>Last name:</label>
        <input type="text" name="lastName" class="form-control"
               value="${worker.lastName}" required/>
        <br>
        <label>Working experience:</label>
        <input type="number" name="workingExperience" class="form-control"
               value="${worker.workingExperience}" required/>
        <br>
        <label>Hire Date: </label>
        <input type="date" name="hireDate" value="${worker.hireDate}"
               class="form-control" required>
        <br>
        <label>
            <select name="position" class="form-control" required>
                <c:forEach var="positions" items="${positionList}">
                    <option selected disabled hidden>Choose position</option>
                    <option value="${positions.id}">
                            ${positions.jobName}
                    </option>
                </c:forEach>
            </select>
        </label>

        <br>
        <input type="submit" value="Save" class="btn-primary"/>

        <c:choose>
            <c:when test="${worker.id == null}">
                <input type="hidden" name="action" value="workerAdd">
            </c:when>
            <c:when test="${worker.id != null}">
                <input type="hidden" name="action" value="workerUpdate">
            </c:when>
        </c:choose>

    </form>


    <p>
        <a href="worker?action=workerList">Back to List</a>
    </p>
</div>
</body>

</html>











