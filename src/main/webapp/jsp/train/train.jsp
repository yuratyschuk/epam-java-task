<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

<head>
    <title>Train</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <jsp:include page="../../templates/header.jsp"></jsp:include>
</head>

<body>


<div id="container">
    <h3>Train</h3>

    <form action="train" method="post">

        <input type="hidden" name="trainId" value="${train.id}"/>

        <label>Train number:
        <input type="text" name="trainNumber"
               value="${train.trainNumber}" class="form-control" required/>
        </label>
        <br>
        <label>Train name:
        <input type="text" name="trainName"
               value="${train.trainName}" class="form-control" required/>
        </label>
        <br>
        <label>Max number of carriages:
            <input type="number" name="maxNumberOfCarriages"
                   value="${train.maxNumberOfCarriages}" class="form-control" required/>
        </label>
        <br>
        <label>
            <select name="trainType" class="form-control" required>
                <option selected disabled hidden>Choose type</option>
                <c:forEach var="trainType" items="${trainTypeEnum}">
                    <option value="${trainType}">
                            ${trainType}
                    </option>
                </c:forEach>
            </select>
        </label>
        <br>

        <input type="submit" value="Save" class="btn-primary"/>

        <c:choose>
            <c:when test="${train.id == null}">
                <input type="hidden" name="action" value="trainAdd">
            </c:when>
            <c:when test="${train.id != null}">
                <input type="hidden" name="action" value="trainUpdate">
            </c:when>
        </c:choose>

    </form>

    <p>
        <a href="train?action=trainList">Back to List</a>
    </p>
</div>
</body>

</html>
