<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trip list</title>
</head>
<body>

<a href="trip?action=tripAdd">Add new Trip</a>
    <table>
        <tr>
            <th>Departure time</th>
            <th>Departure place</th>
            <th>Arrival time</th>
            <th>Arrival name</th>
            <th>Ticket price</th>
            <th>Number of places</th>
            <th>Train name</th>
        </tr>
        <c:forEach items="${tripList}" var="trip">
        <tr>
            <td>${trip.departureTime}</td>
            <td>${trip.route.departurePlace.placeName}</td>
            <td>${trip.arrivalTime}</td>
            <td>${trip.route.arrivalPlace.placeName}</td>
            <td>${trip.ticketPrice}</td>
            <td>${trip.numberOfPlaces}</td>
            <td>${trip.train.trainName}</td>
            <td><a href="trip?action=tripDelete&tripId=${trip.id}">Delete</a></td>
            <td><a href="trip?action=tripUpdate&tripId=${trip.id}">Update</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
