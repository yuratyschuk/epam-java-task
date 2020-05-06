<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
${trip.route.departurePlace.placeName}
${trip.departureTime}
${trip.route.arrivalPlace.placeName}
${trip.arrivalTime}
${trip.numberOfCarriages}

<c:forEach items="${trip.stopSet}" var="stops">
    ${stops.name}
    ${stops.duration}
</c:forEach>

<button type="submit" value="Buy ticket" size="20"><a href="ticket?action=buyTicket&tripId=${trip.id}">Buy</a></button>

</body>
</html>
