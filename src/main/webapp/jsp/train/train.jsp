<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>Train</title>

</head>

<body>


<div id="container">
    <h3>Train</h3>

    <form action="train" method="post">

        <input type="hidden" name="trainId" value="${train.id}" />

        <table>
            <tbody>
            <tr>
                <td><label>Train number:</label></td>
                <td><input type="text" name="trainNumber"
                           value="${train.trainNumber}" /></td>
            </tr>

            <tr>
                <td><label>Train name:</label></td>
                <td><input type="text" name="trainName"
                           value="${train.trainName}" /></td>
            </tr>

            <tr>
                <td><label>Max number of carriages:</label></td>
                <td><input type="text" name="maxNumberOfCarriages"
                           value="${train.maxNumberOfCarriages}" /></td>
            </tr>


            <tr>
                <td>
                    <label>
                        <select name="trainType">
                            <c:forEach var="trainType" items="${trainTypeList}">
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
                <td><input type="submit" value="Save" class="save" /></td>
            </tr>

            </tbody>
        </table>
    </form>

    <p>
        <a href="train?action=listTrain">Back to List</a>
    </p>
</div>
</body>

</html>
