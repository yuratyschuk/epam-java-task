<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket list</title>
</head>
<body>
<table class="table">
    <tr>
        <th>Departure place</th>
        <th>Departure time</th>
        <th>Arrival Place</th>
        <th>Arrival time</th>
        <th>Price</th>
        <th>Time when bought</th>
    </tr>
    <c:forEach items="${ticketList}" var="ticket">
        <tr>
            <td>${ticket.trip.route.departurePlace.placeName}</td>
            <td>${ticket.trip.departureTime}</td>
            <td>${ticket.trip.route.arrivalPlace.placeName}</td>
            <td>${ticket.trip.arrivalTime}</td>
            <td>${ticket.trip.ticketPrice}</td>
            <td>${ticket.timeWhenTicketWasBought}</td>
            <td><a href="ticket?action=ticketDelete&ticketId=${ticket.id}">Delete</a></td>
            <td><a href="ticket?action=ticketUpdate&ticketId=${ticket.id}">Update</a></td>
        </tr>
    </c:forEach>
</table>

<h2> <c:if test="${empty ticketList}">Ticket table is empty</c:if></h2>
</body>
</html>
