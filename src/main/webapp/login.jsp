<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page isELIgnored="false" %>

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
    <c:set var="currentLocale" value="${not empty param.locale ? param.locale : not empty currentLocale ? currentLocale : sessionScope.currentLocale}" scope="session" />
    <fmt:setLocale value="${currentLocale}" />
    <%----%>
    <fmt:setBundle basename="messages"/>
    <title>Login</title>
</head>
<body>
<%@ include file="./fragments/header.jsp" %>

<main class="d-flex justify-content-center container sm-5 my-5">
    <div class="col-6 justify-items-center">
        <h2 class="container"><fmt:message key="sign.in"/></h2>
        <form action="${ctxPath}/app/login" method="post">
            <c:if test="${requestScope.get('error') != null}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert" >
                    <p><fmt:message key="${error}"/></p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <div class="mb-3">
                <label for="ControlInput1" class="form-label"><fmt:message key="field.email"/></label>
                <input type="email"  class="form-control" id="ControlInput1" name="email" value="${requestScope.get("email")}">
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput2" class="form-label"><fmt:message key="field.password"/></label>
                <input type="password"  class="form-control" id="exampleFormControlInput2" name="password">
            </div>
            <div>
                <button class="btn btn-primary" type="submit"><fmt:message key="sign.in"/> </button>
                <a href="${ctxPath}/app/registration"><fmt:message key="title.registration"/></a>
            </div>
        </form>

    </div>
</main>

<%@ include file="./fragments/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>
