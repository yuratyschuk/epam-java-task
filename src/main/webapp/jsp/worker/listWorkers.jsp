<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

</head>

<body>


<a href="worker?action=insert">Add new Worker</a>
<table class="table">
    <tr>
        <th>Worker name</th>
        <th>Position</th>
        <th>Working experience</th>
        <th>Hire date</th>
    </tr>
    <c:forEach items="${workerList}" var="worker">
        <tr>
            <td>${worker.firstName} ${worker.lastName}</td>
            <td>${worker.position.jobName}</td>
            <td>${worker.workingExperience}</td>
            <td>${worker.hireDate}</td>
            <td>
                <a href="worker?action=edit&workerId=${worker.id}">Update</a>
            </td>
            <td>

                <a href="worker?action=delete&workerId=${worker.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
