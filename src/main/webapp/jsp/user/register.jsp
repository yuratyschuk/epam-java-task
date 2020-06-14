<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<form action="user" method="post">

    <input type="hidden" name="userId" value="${user.id}">

    <label>
        First name:
        <input type="text" name="firstName" class="form-control" value="${user.firstName}" required>
    </label>
    <br>
    <label>
        Last name:
        <input type="text" name="lastName" class="form-control" value="${user.lastName}" required>
    </label>
    <br>
    <label>
        Username:
        <input type="text" name="username" class="form-control" value="${user.username}" required>
    </label>
    <br>
    <label>
        Email:
        <input type="email" name="email" class="form-control" value="${user.email}" required>
    </label>
    <br>
    <label>
        Password:
        <input type="password" name="password" class="form-control" required>
    </label>

    <input type="hidden" name="action" value="userRegister">
    <br>
    <button type="submit" class="btn-primary">Register</button>
</form>

${registerValidation}
</body>
</html>
