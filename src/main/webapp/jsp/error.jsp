
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Oh! Error happened:(</h1>
<br>
<h2>Error ${pageContext.errorData.statusCode}</h2>
<br>
${pageContext.exception.message}





</body>
</html>
