<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>

    <title>Search</title>
</head>
<body>
    <form action="trip?action=tripSearchResults">
        <select>
            <c:forEach var="from" items="${departurePlaceList}">
            <option>

            </option>
            </c:forEach>
        </select>

        <select>
            <c:forEach var="to" items="${arrivaPlaceList}">
            <option>

            </option>
            </c:forEach>
        </select>
    </form>
</body>
</html>
