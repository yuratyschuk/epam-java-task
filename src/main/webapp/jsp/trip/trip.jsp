<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trip</title>
</head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<body>
<form action="trip" method="post">

    <input type="hidden" name="tripId" value="${trip.id}">

    <label>
        Departure time:
        <input type="date" name="departureTime" class="form-control" value="${trip.departureTime}" required>
    </label>
    <br>
    <label>
        Departure place:
        <select name="departurePlaceId" class="form-control" required>
            <option selected disabled hidden>Choose departue place</option>
            <c:forEach items="${placesList}" var="places">
                <option value="${places.id}"
                    ${trip.route.departurePlace.placeName == places.placeName ? 'selected="selected"' : ' '}>
                        ${places.placeName}</option>
            </c:forEach>
        </select>
    </label>
    <br>
    <label>
        Arrival time:
        <input type="date" name="arrivalTime" class="form-control" value="${trip.arrivalTime}" required>
    </label>
    <br>
    <label>
        Arrival place:
        <select name="arrivalPlaceId" class="form-control" required>
            <option selected disabled hidden>Choose arrival place</option>
            <c:forEach items="${placesList}" var="places">
                <option value="${places.id}"
                    ${trip.route.arrivalPlace.placeName == places.placeName ? 'selected="selected"' : ''}>
                        ${places.placeName}</option>
            </c:forEach>
        </select>
    </label>

    <label>
        Stops:
        <select name="stopPlaceId" multiple="multiple" class="form-control" required>
            <c:forEach items="${placesList}" var="places">
                <option value="${places.id}">
                        ${places.placeName}
                </option>
            </c:forEach>
        </select>
    </label>
    <br>
    <label>
        Trip type:
        <select name="trainType" id="trainType" class="form-control" required>
            <option value="PASSENGER">Passenger</option>
            <option value="CARGO">Cargo</option>
            <option value="MULTI">Multi</option>
        </select>
    </label>
    <br>

    <label>
        Number Of carriages:
        <input type="number" name="numberOfCarriages" class="form-control"
               value="${trip.numberOfCarriages}" required>
    </label>
    <br>
    <div id="passenger" style="display: none">
        <label>
            Price:
            <input type="number" name="price" class="form-control" value="${trip.ticketPrice}" required>
        </label>
        <br>
        <label>
            Number of places:
            <input type="text" name="numberOfPlaces" class="form-control" value="${trip.numberOfPlaces}">
        </label>
        <br>
        <label>
            Train:
            <select name="trainId" class="form-control" required>
                <option selected disabled hidden>Choose train name</option>
                <c:forEach items="${trainList}" var="train">
                    <c:if test="${train.trainType == 'PASSENGER' || train.trainType == 'MULTI'}">

                        <option value="${train.id}">${train.trainName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </label>
    </div>
    <br>
    <div id="cargo" style="display: none">
        <label>
            Train:
            <select name="trainId" class="form-control" required>
                <option selected disabled hidden>Choose train name</option>
                <c:forEach items="${trainList}" var="train">
                    <c:if test="${train.trainType == 'CARGO'}">
                        <option value="${train.id}">${train.trainName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </label>
    </div>
    <br>
    <button type="submit" value="Save" class="btn-primary">Save</button>

        <c:choose>
        <c:when test="${trip.id == null}">
        <input type="hidden" name="action" value="tripAdd">
        </c:when>
        <c:when test="${trip.id != null}">
        <input type="hidden" name="action" value="tripUpdate">
        </c:when>
        </c:choose>

</form>
<script>
    $('#trainType').on('change', function () {
        if ($(this).val() === "PASSENGER" || $(this).val() === "MULTI") {
            $("#passenger").show()
            $("#cargo").hide()
        } else {
            $("#cargo").show()
            $("#passenger").hide()
        }
    });
</script>
</body>

</html>
