<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Update user info</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<a href="user?action=userChangePassword">Change password</a>

<form action="user" method="post">


    <label>
        Enter new first name:
        <input type="text" name="firstName" class="form-control" value="${user.firstName}" required>
    </label>
    <br>
    <label>
        Enter new last name:
        <input type="text" name="lastName" class="form-control" value="${user.lastName}" required>
    </label>
    <br>
    <label>
        Enter new username:
        <input type="text" name="username" class="form-control" value="${user.username}" required>
    </label>
    <br>
    <label>
        Enter new email:
        <input type="email" name="email" class="form-control" value="${user.email}" required>
    </label>
    <br>

    <input type="hidden" name="action" value="userUpdateInfo">

    <button type="submit" class="btn-primary">Update info</button>
</form>

${updateValidation}
</body>
</html>
