<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <section>
            <h3><spring:message code="meal.title"/></h3>

            <form class="form-horizontal" id="filter">
                <div class="form-group">

                    <dl>
                        <dt><label for="startDate" class="col-form-label"><spring:message
                                code="meal.startDate"/></label></dt>
                        <dd><input type="date" class="form-control" id="startDate" name="startDate"
                                   placeholder="<spring:message code="meal.dateTime"/>"></dd>
                    </dl>

                    <dl>
                        <dt><label for="endDate" class="col-form-label"><spring:message code="meal.endDate"/></label></dt>
                        <dd><input type="date" class="form-control" id="endDate" name="endDate"
                                   placeholder="<spring:message code="meal.dateTime"/>"></dd>
                    </dl>

                    <dl>
                        <dt><label for="startTime" class="col-form-label"><spring:message
                                code="meal.startTime"/></label></dt>
                        <dd><input type="time" class="form-control" id="startTime" name="startTime"
                                   placeholder="<spring:message code="meal.dateTime"/>"></dd>
                    </dl>

                    <dl>
                        <dt><label for="endTime" class="col-form-label"><spring:message code="meal.endTime"/></label></dt>
                        <dd><input type="time" class="form-control" id="endTime" name="endTime"
                                   placeholder="<spring:message code="meal.dateTime"/>"></dd>
                    </dl>
                </div>
                <a class="btn btn-primary" type="button" onclick="filter()"><spring:message code="meal.filter"/></a>
                <a class="btn btn-primary" type="button" onclick="cleanfilter()">clear</a>
            </form>

            <hr>
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="meal.add"/>
            </button>
            <%--<a href="meals/create"><spring:message code="meal.add"/></a>--%>
            <hr>
            <table class="table table-striped" id="mealdatatable">
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                    <tr data-mealExceed="${meal.exceed}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a><span class="fa fa-pencil"></span></a></td>
                        <td><a onclick="deleteRow(${meal.id})" class="delete"><span class="fa fa-remove"></span></a>
                        </td>
                            <%--<td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
                            <%--<td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </div>
</div>


<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="calories" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close" aria-hidden="true"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check" aria-hidden="true"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>