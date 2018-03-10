<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<c:set var="user" value="${user}"/>
<jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>

<table style="background-color: gray">
    <tr>
        <td width="5%"></td>
        <td style="color: aliceblue">Подсчет колорий</td>
        <td width="70%"></td>
        <td style="color: aliceblue">${user.name}</td>
        <td>
            <form id="logout" action="users" method="post">
                <input type="hidden" value="login" name="login">
                <button type="submit" style="margin-left: 20px">Выход</button>
            </form>
        </td>
        <td width="5%"></td>
    </tr>
</table>

<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>

    <table>
        <form method="post" action="meals">
            <input type="hidden" name="filter" value="filter">
            <tr>
                <td>От даты: </td>
                <td><input type="date" name="filterFromDate" value="${filterFromDate}"></td>
                <td>От времени: </td>
                <td><input type="time" name="filterFromTime" value="${filterFromTime}"></td>
            </tr>
            <tr>
                <td>До даты: </td>
                <td><input type="date" name="filterToDate" value="${filterToDate}"></td>
                <td>До времени: </td>
                <td><input type="time" name="filterToTime" value="${filterToTime}"></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td><button type="reset">Clean</button></td>
                <td><button type="submit">Filter</button></td>
            </tr>
        </form>
    </table>



    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}&userId=${user.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}&userId=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>