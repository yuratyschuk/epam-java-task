<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <jsp:include page="../../templates/header.jsp"></jsp:include>
</head>
<body>
<table class="table">
    <tr>
        <th>Departure</th>
        <th>Departure time</th>
        <th>Arrival</th>
        <th>Arrival time</th>
        <th>Number of places</th>
        <th>See details</th>
    </tr>
    <c:forEach var="trip" items="${tripList}">
        <tr>
            <td>${trip.route.departurePlace.placeName}</td>
            <td type="date">${trip.departureTime}</td>
            <td>${trip.route.arrivalPlace.placeName}</td>
            <td type="date">${trip.arrivalTime}</td>
            <td>${trip.numberOfCarriages}</td>
            <td>
                <a href="trip?action=tripDetails&tripId=${trip.id}">See details</a>
            </td>
        </tr>
    </c:forEach>

    <h1>${searchTripNull}</h1>
</table>
</body>
</html>
