<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/date.tld"%>

<%@ page isELIgnored="false" %>

<%--<c:set var="currentLocale" value="${not empty param.locale ? param.locale : not empty currentLocale ? currentLocale : sessionScope.currentLocale}" scope="session" />--%>
<%--<fmt:setLocale value="${currentLocale}"/>&lt;%&ndash;&ndash;%&gt;--%>
<%--<fmt:setBundle basename="messages"/>--%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet"/>
    <script src="https://kit.fontawesome.com/73e5d9eb4d.js" crossorigin="anonymous"></script>
    <title>Admin</title>

</head>

<body>
<%@ include file="../fragments/header.jsp" %>

<main class="container">
    <div class="day-container container my-5 row d-flex justify-content-center">
        <h2 class="d-flex justify-content-center"><fmt:message key="go.admin"/></h2>
    </div>
    <div class="row">
        <h5><span><fmt:message key="attendance.stats"/></span>
            <c:if test="${requestScope.get('flash').get('stats') != null}">
                <span> : <my:Date date="${requestScope.get('flash').get('period').get(0)}"/> - <my:Date date="${requestScope.get('flash').get('period').get(1)}"/> --> ${requestScope.get('flash').get('stats')}</span>
            </c:if>
        </h5>
        <form action="${ctxPath}/app/admin/stats" method="post">
            <input type="date" name="date_start"/>
            <input type="date" name="date_end">
            <input type="submit" value="<fmt:message key="submit.button"/>"/>
        </form>
    </div>

    <div class="container success-container">
        <c:if test="${requestScope.get('flash').get('success').equals('movieSession')}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <p><fmt:message key="session.created"/></p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${requestScope.get('flash').get('success').equals('movie')}">
            <div class="alert alert-warningalert-success alert-dismissible fade show" role="alert">
                <p><fmt:message key="movie.created"/></p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
    </div>
    <div class="container session-errors-container">
        <c:if test="${requestScope.get('flash').get('failed').equals('movieSession')}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <p><fmt:message key="movie.session.not.created"/></p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${requestScope.get('flash').get('failed').equals('movie')}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <p><fmt:message key="movie.not.created"/></p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${requestScope.get('flash').get('errors') != null}">
            <c:forEach var="error" items="${requestScope.get('flash').get('errors')}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <p><fmt:message key="${error}"/></p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <div class="row create-section">
        <div class="container col mb-5 create-session-container align-content-center">
            <div class="row">
                <h5><fmt:message key="create.session"/></h5>
            </div>
            <form class="col" action="${ctxPath}/app/admin/sessions" method="post">
                <!--    Add session -->
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupSelect01"><fmt:message key="option.day"/></label>
                    <input type="date" id="inputGroupSelect01" name="day_of_session">
                </div>
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupSelect02"><fmt:message key="option.time"/></label>
                    <select class="form-select" id="inputGroupSelect02" name="time_start">
                        <!--                    Generate from db all available start times -->
                        <c:forEach var="hour" begin="9" end="22" step="1" varStatus="i">
                            <option value="<c:out value="${hour < 10 ? '0' : ''}${hour}:00:00" />">
                              <c:out value="${hour}:00"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupSelect03"><fmt:message key="option.movie"/></label>
                    <select class="form-select" id="inputGroupSelect03" name="movie_id">
                        <!--                    Generate from db all available movies -->
                        <c:forEach var="movie" items="${requestScope.get('movies')}">
                            <option value="${movie.getId()}">
                                <c:out value="${(currentLocale== 'en') ? movie.getTitleEn() : movie.getTitleUa()}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <input class="btn btn-success w-100" type="submit" value="<fmt:message key="create.session"/>">
            </form>

        </div>
        <div class="container col mb-5 create-movie-container">
            <div class="row">
                <h5><fmt:message key="create.movie"/></h5>
            </div>
            <div class="row">
                <form class="col" action="${ctxPath}/app/admin/movies" method="post">
                    <!--    Add movie   -->
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="title.en"/></span>
                        <input
                                type="text"
                                name="title_en"
                                class="form-control" aria-label="Sizing example input"
                                aria-describedby="inputGroup-sizing-default">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="title.ua"/></span>
                        <input type="text" name="title_ua" class="form-control" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="release.year"/></span>
                        <input type="text" name="release_year" class="form-control" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="running.time"/></span>
                        <input type="text" name="running_time" class="form-control" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="poster.file"/></span>
                        <input type="file" name="poster" class="form-control" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default">
                    </div>
                    <input class="btn btn-success w-100" type="submit" value="<fmt:message key="add.button"/>">
                </form>
            </div>
        </div>
    </div>
</main>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
<script src="${ctxPath}/static/js/admin.js"></script>
</body>
</html>