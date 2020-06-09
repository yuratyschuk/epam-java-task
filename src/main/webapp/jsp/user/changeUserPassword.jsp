<%--
  Created by IntelliJ IDEA.
  User: yurat
  Date: 6/9/2020
  Time: 7:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password</title>
</head>
<body>
<form method="post">
    <label>
        Enter new password:
        <input type="password" name="password" required>
    </label>

    <label>
        Repeat new password:
        <input type="password" name="repeatedPassword" required>
    </label>

    <input type="hidden" name="action" value="userChangePasswordPost">
    <button type="submit">Change password</button>
</form>

${passwordValidation}
</body>
</html>
