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
<table>
    <c:forEach var="meal" items="${listMeals}">
        <c:set var="color" value="green" />
        <c:if test="${meal.exceed}">
            <c:set var="color" value="red" />
        </c:if>
        <tr style="color: ${color}" >
                <td>${f:formatLocalDateTime(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
