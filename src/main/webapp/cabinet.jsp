<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
  <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" />
  <script src="https://kit.fontawesome.com/73e5d9eb4d.js" crossorigin="anonymous"></script>
  <title>Home</title>

</head>

<body>
<%@ include file="./fragments/header.jsp" %>
<main class="container">
  <div class="row my-5">
    <h2><fmt:message key="personal.cabinet" /></h2>
  </div>
  <div class="row my-5">
    <div class="col">
      <h4><span><c:out value="${sessionScope.get('user').getEmail()}"/></span></h4>
    </div>
    <div class="col">
      <h4 class="row"><fmt:message key="your.tickets"/></h4>
      <div>
          <c:forEach var="ticket" items="${requestScope.get('tickets')}">
            <div class="alert alert-dark" role="alert">
              <p>
                <span><c:out value="${currentLocale == 'en' ? ticket.getMovieSession().getMovie().getTitleEn() : ticket.getMovieSession().getMovie().getTitleUa()}"/></span>
              </p>
              <p>
                <span><fmt:message key="day-${ticket.getMovieSession().getDayOfSession().getDayOfWeek().getValue()}"/></span>
              </p>
              <p>
                <span><c:out value="${ticket.getMovieSession().getTimeStart()}"/></span>
              </p>
              <p>
                <span><fmt:message key="seat.row"/></span><span> - ${ticket.getSeat().getRow()}</span>
              </p>
              <p>
                <span><fmt:message key="seat.number" /></span><span> - ${ticket.getSeat().getNumber()}</span>
              </p>
            </div>
          </c:forEach>
      </div>
    </div>
  </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>