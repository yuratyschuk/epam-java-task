<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Positions </title>
</head>
<body>

<a href="position?action=positionAdd">Add new Position</a>
<c:choose>
    <c:when test="${param.action.equals('positionListActive')}">
        <a href="position?action=positionList">All positions</a>
    </c:when>
    <c:when test="${param.action.equals('positionList')}">
        <a href="position?action=positionListActive">Active positions</a>
    </c:when>
</c:choose>

<table class="table">
    <tr>
        <th>Job</th>
        <th>Salary</th>
        <th>Active</th>
    </tr>
    <c:forEach items="${positionList}" var="position">

        <tr>
            <td>${position.jobName}</td>
            <td>${position.salary}</td>
            <td>
                <c:choose>
                    <c:when test="${position.active == true}">Yes</c:when>
                    <c:when test="${position.active==false}">No</c:when>
                </c:choose>
            </td>
            <td><a href="position?action=positionDelete&positionId=${position.id}">Delete</a></td>
            <td><a href="position?action=positionUpdate&positionId=${position.id}">Update</a></td>
        </tr>
    </c:forEach>
</table>

<c:if test="${empty positionList}">Position table is empty</c:if>
</body>
</html>
