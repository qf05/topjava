<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals Edit</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal" accept-charset="UTF-8">
    <input type="hidden" name="id"
           value="${meal.id}"/>
    Date : <input
        type="datetime-local" name="addDate" placeholder="yyyy-MM-dd'T'hh:mm"
        value="<c:out value="${meal.dateTime}" />"/> <br/>
    Description : <input
        type="text" name="addDis"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="number" name="addCalories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input
            type="submit" value="Submit"/>
</form>
</body>
</html>