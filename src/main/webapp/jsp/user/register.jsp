<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Register</title>
</head>
<body>
<form action="user" method="post">

    <input type="hidden" name="userId" value="${user.id}" >

    <label>
        First name:
        <input type="text" name="firstName" value="${user.firstName}" required>
    </label>
    <label>
        Last name:
        <input type="text" name="lastName" value="${user.lastName}" required>
    </label>
    <label>
        Username:
        <input type="text" name="username" value="${user.username}" required>
    </label>
    <label>
        Email:
        <input type="email" name="email" value="${user.email}" required>
    </label>

    <label>
        Password:
        <input type="password" name="password" required>
    </label>

    <input type="hidden" name="action" value="userRegister">

    <button type="submit">Register</button>
</form>
</body>
</html>
