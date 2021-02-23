<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/date.tld"%>
<%@ taglib prefix="fmy" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

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
  <title>Buy</title>
</head>

<body>
<%@ include file="./fragments/header.jsp" %>
<main class="container">
  <div class="row mt-5">
    <div class="col">
      <div class="d-flex justify-content-center border border-dark w-100 mb-5">
        <p class="h-10 mb-0"><fmt:message key="screen"/></p>
      </div>
      <div class="d-flex justify-content-center">
        <form  class="d-flex flex-column justify-content-center" action="${ctxPath}/app/buy/purchase?id=${requestScope.get('selectedSession').getId()}" method="post">
          <table class="table align-middle">
              <c:forEach var="row" items="${requestScope.get('rows').entrySet()}">
                <tr>
                  <td><c:out value="${row.getKey()}"/></td>
                    <c:forEach var="seat" items="${row.getValue()}">
                      <td
                        <c:forEach var="ticket" items="${requestScope.get('tickets')}">
                          <c:if test="${ticket.getSeat().equals(seat)}">
                            style="background: green;"
                          </c:if>
                        </c:forEach>
                      >
                        <input
                               <c:out value="${requestScope.get('seatSession').contains(seat) ? 'disabled' : ''}"/>
                               <c:out value="${requestScope.get('seatSession').contains(seat) ? 'checked' : ''}"/>
                               type="checkbox" name="seat_id"
                               value="${seat.getId()}">
                      </td>
                    </c:forEach>
                  <td><c:out value="${row.getKey()}" /></td>
                </tr>
              </c:forEach>
          </table>
          <c:if test="${requestScope.get('seatSession').size() < requestScope.get('seatsTotal') and sessionScope.get('user') != null}">
            <input class="btn btn-success my-2" type="submit" value="<fmt:message key="buy.ticket"/>"/>
          </c:if>
          <c:if test="${sessionScope.get('user') == null}">
            <a href="${ctxPath}/app/login" class="btn btn-warning"><fmt:message key="please.sign.in"/></a>
          </c:if>

        </form>
      </div>
      <c:if test="${requestScope.get('flash').get('error') != null}">
        <div class="alert alert-warning alert-dismissible fade show" role="alert" >
          <p><fmt:message key="${requestScope.get('flash').get('error')}"/></p>
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      </c:if>
      <c:if test="${requestScope.get('tickets').size() > 0}">
        <div>
          <p><fmt:message key="your.tickets"/></p>
          <div>
              <c:forEach var="ticket" items="${requestScope.get('tickets')}" varStatus="i">
                <div class="alert alert-dark" role="alert">
                  <p>
                      <span><fmt:message key="ticket"/><span><c:out value=" #  ${ticket.getId()}"/></span>
                  </p>
                  <p>
                      <span><fmt:message key="seat.row"/></span><span><c:out value=" - ${ticket.getSeat().getRow()}"/>
                  </p>
                  <p>
                      <span><fmt:message key="seat.number" /></span><span><c:out value=" - ${ticket.getSeat().getNumber()}"/></span>
                  </p>
                </div>
              </c:forEach>
          </div>
          <a href="${ctxPath}/app/home" class="btn btn-primary"><fmt:message key="go.home"/></a>
        </div>
      </c:if>
    </div>
    <div class="col d-flex flex-column justify-content-start align-items-center">
      <div>
        <h5><c:out value="${currentLocale == 'en' ? requestScope.get('selectedSession').getMovie().getTitleEn() : requestScope.get('selectedSession').getMovie().getTitleUa()}" /></h5>
        <p class="card-text"><my:Date date="${requestScope.get('selectedSession').getDayOfSession()}" /></p>
        <p class="card-text"><fmt:message key="day-${requestScope.get('selectedSession').getDayOfSession().getDayOfWeek().getValue()}" /></p>
        <p class="card-text"><fmt:message key="release.year"/></p>
        <p class="card-text"><c:out value="${requestScope.get('selectedSession').getMovie().getReleaseYear()}"/></p>
        <p class="card-text"><fmt:message key="time.start" /></p>
        <p class="card-text"><c:out value="${requestScope.get('selectedSession').getTimeStart()}" /></p>
      </div>
    </div>
  </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>