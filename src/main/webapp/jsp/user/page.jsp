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
</head>
<body>
${user.id}
${user.username}
${user.firstName}
${user.lastName}
${user.email}
${user.password}

<a href="user?action=userDelete">Delete account</a>
<a href="user?action=userUpdate">Update info</a>

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

<c:if test="${empty ticketList}">You don't buy any tickets yet</c:if>
</body>
</html>
