<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <title>Buy ticket</title>
</head>
<body>
    <form action="ticket" method="post">

        <label for="firstName">First name: </label>
        <input type="text" name="firstName" id="firstName" value="${ticket.user.firstName}" required>

        <label for="lastName">Last name: </label>
        <input type="text" name="lastName" id="lastName" value="${ticket.user.lastName}" required>

        <label for="email">Email: </label>
        <input type="email" name="email" id="email" value="${ticket.user.email}" required>

        <input type="hidden" name="tripId" value="${ticket.trip.id}">

        <button type="submit" size="20" value="Buy"></button>
    </form>
</body>
</html>
