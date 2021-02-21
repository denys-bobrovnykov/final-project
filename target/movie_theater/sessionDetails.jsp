<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/date.tld"%>
<%@ taglib prefix="fmy" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<%--<c:set var="currentLocale" value="${not empty param.locale ? param.locale : not empty currentLocale ? currentLocale : sessionScope.currentLocale}" scope="session" />--%>
<%--<fmt:setLocale value="${currentLocale}"/>&lt;%&ndash;&ndash;%&gt;--%>
<%--<fmt:setBundle basename="messages"/>--%>

<c:set value="${requestScope.get('selectedSession')}" var="selectedSession" scope="page"/>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
  <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" />
  <script src="https://kit.fontawesome.com/73e5d9eb4d.js" crossorigin="anonymous"></script>
  <title>Details</title>

</head>

<body>
<%@ include file="./fragments/header.jsp" %>

<main class="container my-5">
  <div class="container">
    <div class="card mb-3 border-0">
      <div class="row g-0">
        <div class="col-md-4">
          <img class="container flow" src="${ctxPath}/static/img/poster/${selectedSession.getMovie().getPoster()}" alt="poster">
        </div>
        <div class="col-md-1"></div>
        <div class="col-md-7">
          <div class="card-body d-flex flex-column align-items-center">
            <h5><c:out value="${currentLocale == 'en' ? selectedSession.getMovie().getTitleEn() : selectedSession.getMovie().getTitleUa()}"/></h5>
            <p class="card-text"><fmt:message key="day-${selectedSession.getDayOfSession().getDayOfWeek().getValue()}"/></p>
            <p class="card-text"><my:Date date="${selectedSession.getDayOfSession()}"/></p>
            <p class="card-text"><fmt:message key="release.year"/></p>
            <p class="card-text"><c:out value="${selectedSession.getMovie().getReleaseYear()}"/></p>
            <p class="card-text"><fmt:message key="time.start"/></p>
            <p class="card-text"><c:out value="${selectedSession.getTimeStart()}"/></p>
            <p class="card-text"><fmt:message key="running.time"/></p>
            <p class="card-text" >
              <span><c:out value="${selectedSession.getMovie().getRunningTime()}"/></span> <span><fmt:message key="minutes"/></span>
            </p>
            <div class="container d-flex justify-content-center">
              <c:if test="${sessionScope.get('user') != null}">
                <p>
                  <a class="btn btn-success" href="${ctxPath}/app/buy?locale=${currentLocale}&id=${selectedSession.getId()}"><fmy:message key="buy.ticket"/></a></p>
              </c:if>
              <c:if test="${sessionScope.get('user') == null}">
                <a href="${ctxPath}/app/login" class="btn btn-warning"><fmt:message key="please.sign.in"/></a>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>