<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>Update Worker</title>

</head>

<body>


<div id="container">
    <h3>Update Worker</h3>

    <form action="worker" method="post">

        <input type="hidden" name="command" value="UPDATE" />

        <input type="hidden" name="workerId" value="${worker.id}" />

        <table>
            <tbody>
            <tr>
                <td><label>First name:</label></td>
                <td><input type="text" name="firstName"
                           value="${worker.firstName}" /></td>
            </tr>

            <tr>
                <td><label>Last name:</label></td>
                <td><input type="text" name="lastName"
                           value="${worker.lastName}" /></td>
            </tr>

            <tr>
                <td><label>Working experience:</label></td>
                <td><input type="text" name="workingExperience"
                           value="${worker.workingExperience}" /></td>
            </tr>

            <tr>
                <td><label>Hire Date: </label></td>
                <td><input type="date" name="hireDate" value="${worker.hireDate}"></td>
            </tr>

            <tr>
                <td>
                    <label>
                        <select name="position">
                            <c:forEach var="positions" items="${positionList}">
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
    </form>

    <div style="clear: both;"></div>

    <p>
        <a href="worker?action=listWorkers">Back to List</a>
    </p>
</div>
</body>

</html>











