
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <jsp:include page="../../templates/header.jsp"></jsp:include>
</head>
<body>
<form method="post">
    <label>
        Enter new password:
        <input type="password" name="password" class="form-control" required>
    </label>
    <br>
    <label>
        Repeat new password:
        <input type="password" name="repeatedPassword" class="form-control" required>
    </label>
    <br>
    <input type="hidden" name="action" value="userChangePasswordPost">
    <button type="submit" class="btn-primary">Change password</button>
</form>

${passwordValidation}
</body>
</html>
