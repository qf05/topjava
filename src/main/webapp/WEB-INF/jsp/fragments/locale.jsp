<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<li class="dropdown" style="padding-left: 20px; padding-top: 15px;">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?language=en">English</a></li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?language=ru">Русский</a></li>
    </ul>
</li>

<script type="text/javascript">
    var locale = "${pageContext.response.locale}";
</script>