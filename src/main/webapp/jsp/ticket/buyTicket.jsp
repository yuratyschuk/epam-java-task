<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <title>Buy ticket</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
    <form action="ticket" method="post">

        <input type="hidden" name="tripId" value="${trip.id}">

        <button type="submit" size="20" class="btn-primary" value="Buy"></button>
    </form>

${loginMessage}
</body>
</html>
