<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Position detail</title>
</head>
<body>
<form action="position" method="post">

    <input type="hidden" name="positionId" value="${position.id}" >

    <label>
        Job name:
        <input type="text" name="jobName" value="${position.jobName}" required>
    </label>

    <label>
        Salary
        <input type="number" name="salary" value="${position.salary}" required>
    </label>

    <label>
        Active
        <select name="active">
            <c:choose>
                <c:when test="${position.active == false}">
                    <option value="false" selected="selected">No</option>
                    <option value="true" >Yes</option>
                </c:when>
                <c:otherwise>
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </c:otherwise>
            </c:choose>
        </select>
    </label>

    <c:choose>
        <c:when test="${position.id == null}">
            <input type="hidden" name="action" value="positionAdd">
        </c:when>
        <c:when test="${position.id != null}">
            <input type="hidden" name="action" value="positionUpdate">
        </c:when>
    </c:choose>
    <button type="submit">Save</button>
</form>
</body>
</html>
