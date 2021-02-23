<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html lang="en">
<head>
    <%@ include file="../fragments/head.jsp"%>
    <title>Error</title>
</head>

<body>
<%@ include file="../fragments/header.jsp" %>

    <h1><fmt:message key="error"/></h1>

    <h5>BAD REQUEST</h5>
    <a href="${pageContext.request.contextPath}/app/home"><fmt:message key="go.home" /></a>

<%@ include file="../fragments/footer.jsp" %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>
</html>