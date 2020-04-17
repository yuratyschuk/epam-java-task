<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Buy ticket</title>
</head>
<body>
    <form action="ticket" method="post">
        <label>First name: </label>
        <input type="text" name="firstName">

        <label>Last name: </label>
        <input type="text" name="lastName">

        <label>Email: </label>
        <input type="email" name="email">

        <input type="hidden" name="tripId" value="${trip.id}">

        <button type="submit" size="20" value="Buy"></button>
    </form>
</body>
</html>
