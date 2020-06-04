<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

<head>
    <title>Train</title>

</head>

<body>


<div id="container">
    <h3>Train</h3>

    <form action="train" method="post">

        <input type="hidden" name="trainId" value="${train.id}"/>

        <table>
            <tbody>
            <tr>
                <td><label>Train number:</label></td>
                <td><input type="text" name="trainNumber"
                           value="${train.trainNumber}" required/></td>
            </tr>

            <tr>
                <td><label>Train name:</label></td>
                <td><input type="text" name="trainName"
                           value="${train.trainName}" required/></td>
            </tr>

            <tr>
                <td><label>Max number of carriages:</label></td>
                <td><input type="number" name="maxNumberOfCarriages"
                           value="${train.maxNumberOfCarriages}" required
                /></td>
            </tr>


            <tr>
                <td>
                    <label>
                        <select name="trainType" required>
                            <option selected disabled hidden>Choose type</option>
                            <c:forEach var="trainType" items="${trainTypeEnum}">
                                <option value="${trainType}">
                                        ${trainType}
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save"/></td>
            </tr>

            </tbody>
        </table>

        <c:choose>
            <c:when test="${train.id == null}">
                <input type="hidden" name="action" value="trainAdd">
            </c:when>
            <c:when test="${train.id != null}">
                <input type="hidden" name="action" value="trainUpdate">
            </c:when>
        </c:choose>

    </form>

    <p>
        <a href="train?action=trainList">Back to List</a>
    </p>
</div>
</body>

</html>
