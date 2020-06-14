<!DOCTYPE>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <jsp:include page="../../templates/header.jsp"></jsp:include>
</head>
<body>

<form action="ticket" method="post">
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
    <input type="hidden" name="action" value="buyTicket">
    <input type="hidden" name="tripId" value="${trip.id}">
    <button type="submit" size="20" class="btn-primary">Buy ticket</button>

</form>
${loginMessage}
</body>

</html>
