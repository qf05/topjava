<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<p style="margin-left: 20px"><a href="meals?action=add">Add Meal</a></p>

<table style="margin: 20px; border-collapse: collapse" border="2px" cellpadding="10px">
    <tr>
        <th>Data</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>

    <c:forEach var="meal" items="${listMeals}">
        <c:set var="color" value="${meal.exceed ? 'red' : 'green'}"/>
        <tr style="color: ${color}">
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&Id=<c:out value="${meal.id}"/>">Edit</a></td>
            <td><a href="meals?action=delete&Id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
