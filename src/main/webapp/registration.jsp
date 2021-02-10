<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="currentLocale" value="${not empty param.locale ? param.locale : not empty currentLocale ? currentLocale : 'ua'}" scope="session" />
<fmt:setLocale value="${currentLocale}" />
<%----%>
<fmt:setBundle basename="messages"/>

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
    <title>Registration</title>
</head>
<body>

<%@ include file="../fragments/header.jsp" %>

<main class="d-flex justify-content-center container sm-5 my-5">
    <div class="col-6 justify-items-center">
        <h2 class="container"><fmt:message key="title.registration"/></h2>

        <form action="${ctxPath}/app/registration" method="post">
            <c:if test="${requestScope.get('errors') != null}">
                <c:forEach var="error" items="${requestScope.get('errors')}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <p><fmt:message key="${error}"/></p>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:forEach>
            </c:if>
<%--            <div th:if="${error}" class="alert alert-warning alert-dismissible fade show" role="alert" >--%>
<%--                <p th:text="#{${error.getMessage()}}"></p>--%>
<%--                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>--%>
<%--            </div>--%>
            <div class="mb-3">
                <label for="ControlInput1" class="form-label"><fmt:message key="field.email"/></label>
                <input type="text"
                       value="${requestScope.get("email")}"
                       class="form-control" id="ControlInput1" name="email">
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput2" class="form-label"><fmt:message key="field.password"/></label>
                <input type="password" class="form-control" id="exampleFormControlInput2" name="password">
            </div>
            <div>
                <button class="btn btn-primary" type="submit"><fmt:message key="sign.up"/></button>
            </div>
        </form>

    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>