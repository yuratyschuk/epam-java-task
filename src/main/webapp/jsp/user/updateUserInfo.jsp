<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Update user info</title>
</head>
<body>
<a href="user?action=userChangePassword">Change password</a>

<form action="user" method="post">


    <label>
        Enter new first name:
        <input type="text" name="firstName" value="${user.firstName}" required>
    </label>
    <label>
        Enter new last name:
        <input type="text" name="lastName" value="${user.lastName}" required>
    </label>
    <label>
        Enter new username:
        <input type="text" name="username" value="${user.username}" required>
    </label>
    <label>
        Enter new email:
        <input type="email" name="email" value="${user.email}" required>
    </label>


    <input type="hidden" name="action" value="userUpdateInfo">

    <button type="submit">Update info</button>
</form>
</body>
</html>
