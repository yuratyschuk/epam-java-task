<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Login page</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <jsp:include page="../../templates/header.jsp"></jsp:include>
</head>
<body>
<form action="user" method="post">

    <label>
        Username:
    </label>
        <input type="text" name="username" class="form-control" required>
    <br>
    <label>
        Password:
    </label>
        <input type="password" name="password" class="form-control" required>

    <input type="hidden" name="action" value="userLogin">
    <br>
    <span>${errorMessage}</span>
    <br>
    <button type="submit" class="btn-primary" >Submit</button>
</form>
</body>
</html>
