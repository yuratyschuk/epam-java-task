<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>List of trains</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <jsp:include page="../../templates/header.jsp"></jsp:include>
</head>
<body>
<a href="train?action=trainAdd">Add new Train</a>
<table class="table">
    <tr>
        <th>Train name</th>
        <th>Train number</th>
        <th>Type</th>
        <th>Max number of carriages</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="train" items="${trainList}">
        <tr>
            <td>${train.trainName}</td>
            <td>${train.trainNumber}</td>
            <td>${train.trainType}</td>
            <td>${train.maxNumberOfCarriages}</td>
            <td><a href="train?action=trainDelete&trainId=${train.id}">Delete</a></td>
            <td><a href="train?action=trainUpdate&trainId=${train.id}">Update</a></td>
        </tr>
    </c:forEach>
</table>

<c:if test="${empty trainList}">Train table is empty</c:if>
</body>
</html>
