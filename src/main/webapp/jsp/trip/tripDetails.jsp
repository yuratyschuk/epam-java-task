<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<table class="table">
    <tr>
        <th>Departure place</th>
        <th>Departure time</th>
        <th>Arrival place</th>
        <th>Arrival time</th>
        <th>Number of carriages</th>
        <th>Stop</th>
    </tr>
    <tr>
        <td>${trip.route.departurePlace.placeName}</td>
        <td>
            ${trip.departureTime}</td>
        <td>
            ${trip.route.arrivalPlace.placeName}</td>
        <td>
            ${trip.arrivalTime}</td>
        <td>
            ${trip.numberOfCarriages}</td>
        <td>
            <c:forEach items="${trip.stopSet}" var="stops">
                ${stops.name}
                ${stops.duration}
            </c:forEach>
        </td>
    </tr>
</table>

<br>
<button type="submit" value="Buy ticket" size="20" class="btn-primary">
    <a href="ticket?action=buyTicket&tripId=${trip.id}">Buy</a></button>

</body>

</html>
