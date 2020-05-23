<%--
  Created by IntelliJ IDEA.
  User: yurat
  Date: 07/05/2020
  Time: 00:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Oh! Error happened:(</h1>
<br>
<h2>Error${pageContext.errorData.statusCode}</h2>
<br>
${pageContext.exception.message}





</body>
</html>
