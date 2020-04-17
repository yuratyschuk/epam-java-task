<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Register</title>
</head>
<body>
<form action="user" method="post">
    <label>
        First name:
        <input type="text" name="firstName">
    </label>
    <label>
        Last name:
        <input type="text" name="lastName">
    </label>
    <label>
        Username:
        <input type="text" name="username">
    </label>
    <label>
        Email:
        <input type="email" name="email">
    </label>

    <label>
        Password:
        <input type="password" name="password">
    </label>

    <input type="hidden" name="action" value="register">

    <button type="submit">Register</button>
</form>
</body>
</html>
