<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yurat
  Date: 16/04/2020
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<table class="table">
    <tr>
        <th>id</th>
        <th>username</th>
        <th>first name</th>
        <th>last name</th>
        <th>email</th>
        <th>password</th>
    </tr>
    <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.email}</td>
        <td>${user.password}</td>
        <td><a href="user?action=userDelete">Delete account</a></td>
        <td><a href="user?action=userUpdate">Update info</a></td>
        <td><a href="user?action=userExit">Exit</a></td>
    </tr>

</table>

<br><br>
<h1>Tickets</h1>
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
        </tr>
    </c:forEach>
</table>

<c:if test="${empty ticketList}">You didin't buy any tickets yet</c:if>
</body>
</html>
