<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table class="table-bordered">
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
                <a href="trip?action=details&tripId=${trip.id}">See details</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
