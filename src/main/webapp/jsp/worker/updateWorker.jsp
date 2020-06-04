<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">

<head>
    <title>Update Worker</title>

</head>

<body>


<div id="container">
    <h3>Update Worker</h3>

    <form action="worker" method="post">


        <input type="hidden" name="workerId" value="${worker.id}" />

        <table>
            <tbody>
            <tr>
                <td><label>First name:</label></td>
                <td><input type="text" name="firstName"
                           value="${worker.firstName}" required/></td>
            </tr>

            <tr>
                <td><label>Last name:</label></td>
                <td><input type="text" name="lastName"
                           value="${worker.lastName}" required/></td>
            </tr>

            <tr>
                <td><label>Working experience:</label></td>
                <td><input type="number" name="workingExperience"
                           value="${worker.workingExperience}" required/></td>
            </tr>

            <tr>
                <td><label>Hire Date: </label></td>
                <td><input type="date" name="hireDate" value="${worker.hireDate}" required></td>
            </tr>

            <tr>
                <td>
                    <label>
                        <select name="position" required>
                            <c:forEach var="positions" items="${positionList}">
                                <option selected disabled hidden>Choose position</option>
                                <option value="${positions.id}">
                                        ${positions.jobName}
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

        <c:choose>
            <c:when test="${worker.id == null}">
                <input type="hidden" name="action" value="workerAdd">
            </c:when>
            <c:when test="${worker.id != null}">
                <input type="hidden" name="action" value="workerUpdate">
            </c:when>
        </c:choose>

    </form>


    <p>
        <a href="worker?action=listWorkers">Back to List</a>
    </p>
</div>
</body>

</html>











