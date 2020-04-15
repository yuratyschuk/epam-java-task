<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>

    <title>Search</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<form action="trip?action=tripSearchResults" method="post">
    <select name="from">
        <c:forEach var="from" items="${placesList}">
            <option value="${from.id}">
                    ${from.placeName}
            </option>
        </c:forEach>
    </select>

    <select name="to">
        <c:forEach var="to" items="${placesList}">
            <option value="${to.id}">
                    ${to.placeName}
            </option>
        </c:forEach>
    </select>

    <button type="submit" value="Search"></button>
</form>
</body>
</html>
