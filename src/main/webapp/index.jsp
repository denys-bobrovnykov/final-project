<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<%--    Set Locale --%>
<c:set var="currentLocale" value="${not empty param.locale ? param.locale : not empty currentLocale ? currentLocale : 'ua'}" scope="session" />
<fmt:setLocale value="${currentLocale}" />
<%--                --%>

<%--    Set bundle  --%>
<fmt:setBundle basename="messages"/>
<%--                --%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <!-- Bootstrap CSS -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" />
    <script src="https://kit.fontawesome.com/73e5d9eb4d.js" crossorigin="anonymous"></script>
    <title>Home</title>

</head>

<body>
<%@ include file="../fragments/header.jsp" %>
<c:if test="${param.get('success').equals('login')}">
    <div class="alert alert-success alert-dismissible fade show" role="alert" >
        <p><fmt:message key="success.login"/></p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${param.get('success').equals('registration')}">
    <div class="alert alert-success alert-dismissible fade show" role="alert" >
        <p><fmt:message key="success.register"/></p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:out value="${sessionScope.get('user')}"></c:out>
<c:out value="${currentPageUri}"></c:out>

<main class="container">
    <section class="container schedule-section row my-5">
        <form action="${ctxPath}/app/home" method="get">
            <div>
                <input type="text" name="search" placeholder="<fmt:message key='title.placeholder'/>"/>
                <button type="submit"><fmt:message key="search.button"></fmt:message></button>
                <button type="submit"><fmt:message key="clear.filter.button"></fmt:message> </button>
            </div>
        </form>
        <table class="table align-middle table-striped">
            <thead  th:replace="fragments/table_header :: table_header">
            </thead>
            <tbody class="text-center">
            <c:forEach var="row" items="${requestScope.get('rows')}">

            </c:forEach>
<%--            <th:block th:each="row : ${rows}">--%>
<%--                <tr>--%>
<%--                    <th scope="row" th:utext="${row.getDayOfSession()}">Date</th>--%>
<%--                    <td th:utext="#{${'day-' + row.getDayOfSession().getDayOfWeek().getValue() + '-pag'}}">Day</td>--%>
<%--                    <td th:utext="${row.getTimeStart()}">Time</td>--%>
<%--                    <td class="table-data-title"><a--%>
<%--                            th:href="@{/home/details/{id}(id=${row.getId()})}"--%>
<%--                            th:utext="${#locale.getLanguage() == 'en' ? row.getMovie().getTitleEn() : row.getMovie().getTitleUa()}"></a></td>--%>
<%--                    <td th:utext="${row.getSeatsAvail()}">FreeSeats</td>--%>
<%--                    <td sec:authorize="hasAuthority('ADMIN')">--%>
<%--                        <form action="#" th:action="@{/admin/sessions/{id}(id=${row.getId()})}" method="post">--%>
<%--                            <input th:type="hidden" name="_method" value="DELETE">--%>
<%--                            <input type="submit" th:value="#{remove}">--%>
<%--                        </form>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </th:block>--%>
            </tbody>
        </table>
        <div class="row">
        </div>
    </section>

    <div th:replace="fragments/layout :: pagination" />

</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>