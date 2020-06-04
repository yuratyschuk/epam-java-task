<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Login page</title>
</head>
<body>
<form action="user" method="post">
    <label>
        Username:
    </label>
        <input type="text" name="username" required>
    <label>
        Password:
    </label>
        <input type="password" name="password" required>

    <input type="hidden" name="action" value="userLogin">
    <button type="submit" >Submit</button>
</form>
</body>
</html>
