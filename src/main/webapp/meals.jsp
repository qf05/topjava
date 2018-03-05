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

<table style="margin: 20px; border-collapse: collapse" border="2px" cellpadding="5px">
    <caption>Add</caption>
    <tr>
        <th>Data</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Отправить</th>
    </tr>
    <form accept-charset="UTF-8"  action="" method="post">
        <input type="hidden" name="add" value="add">
        <tr>
            <td><label>
                <input type="datetime-local" name="addDate" placeholder="YYYY-MM-DD'T'hh:mm:ss">
            </label></td>
            <td><label>
                <input type="text" name="addDis">
            </label></td>
            <td><label>
                <input type="number" name="addColories">
            </label></td>
            <td><input type="submit" value="Отправить"></td>
        </tr>
    </form>
</table>

<table style="margin: 20px; border-collapse: collapse" border="2px" cellpadding="5px">
    <caption>Redact</caption>
    <tr>
        <th>Data</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Отправить</th>
    </tr>
    <form accept-charset="UTF-8"  action="" method="post">
        <input type="hidden" name="redact" value="redact">
        <tr>
            <td><label>
                <input type="datetime-local" name="reDate" placeholder="YYYY-MM-DD'T'hh:mm:ss">
            </label></td>
            <td><label>
                <input type="text" name="reDis">
            </label></td>
            <td><label>
                <input type="number" name="reColories">
            </label></td>
            <td><input type="submit" value="Отправить"></td>
        </tr>
    </form>
</table>

<table style="margin: 20px; border-collapse: collapse" border="2px" cellpadding="10px">
    <tr>
        <th>Data</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <c:forEach var="meal" items="${listMeals}">
        <c:set var="color" value="${meal.exceed ? 'red' : 'green'}"/>
        <tr style="color: ${color}">
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form accept-charset="UTF-8" action="" method="post">
                    <input type="hidden" name="delete" value="delete">
                    <input type="hidden" value="${meal.id}" name="del">
                    <input type="submit" value="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
