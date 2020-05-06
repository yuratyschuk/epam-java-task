<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <title>Buy ticket</title>
</head>
<body>
    <form action="ticket" method="post">
        <label for="firstName">First name: </label>
        <input type="text" name="firstName" id="firstName" ${ticket.user.firstName}>

        <label for="lastName">Last name: </label>
        <input type="text" name="lastName" id="lastName" ${ticket.user.lastName}>

        <label for="email">Email: </label>
        <input type="email" name="email" id="email" ${ticket.user.email}>

        <input type="hidden" name="tripId" value="${trip.id}">

        <button type="submit" size="20" value="Buy"></button>
    </form>
</body>
</html>
