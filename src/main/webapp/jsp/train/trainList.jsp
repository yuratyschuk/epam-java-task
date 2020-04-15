<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<a href="train?action=insert">Add new Train</a>
    <table class="table-bordered">
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
            <td><a href="train?action=delete&trainId=${train.id}">Delete</a></td>
            <td><a href="train?action=edit&trainId=${train.id}">Update</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
