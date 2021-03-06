<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html lang="en">
<head>
    <%@ include file="./fragments/head.jsp"%>
    <title>Home</title>
</head>

<body>
<%@ include file="./fragments/header.jsp" %>
<c:if test="${requestScope.get('flash').get('success').equals('login')}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <p><fmt:message key="success.login"/></p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${requestScope.get('flash').get('success').equals('registration')}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <p><fmt:message key="success.register"/></p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${requestScope.get('flash').get('success').equals('removed')}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <p><fmt:message key="remove.session"/></p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<main class="container">
    <section class="container schedule-section row my-5">
        <form action="${ctxPath}/app/home" method="get">
            <div>
                <input type="text" name="value"/>
                <select name="search">
                    <option selected value="">----</option>
                    <option value="m.title_${currentLocale}"><fmt:message key="title.placeholder"/></option>
                    <option value="m.release_year"><fmt:message key="release.year"/></option>
                </select>
                <button type="submit"><fmt:message key="search.button"></fmt:message></button>
                <button type="submit"><fmt:message key="clear.filter.button"></fmt:message></button>
            </div>
        </form>
        <table class="table align-middle table-striped">
            <thead class="text-center">
            <tr class="table-dark">
                <th scope="col">
                    <a href="${currentUri}?sort=ms.day_of_session&sort=ms.time_start&sort_dir=${requestScope.get('revSortDir')}&locale=${currentLocale}">
                        <fmt:message key="date"/>
                    </a></th>
                <th scope="col"><span><fmt:message key="weekday"/></span></th>
                <th scope="col"><a
                        href="?sort=ms.time_start&sort_dir=${requestScope.get('revSortDir')}&page=${requestScope.get('current_page')}&search=${requestScope.get('search')}&locale=${currentLocale}">
                    <fmt:message key="time.start"/>
                </a></th>
                <th scope="col"><a
                        href="?sort=m.title_<fmt:message key="title.language"/>&sort_dir=${requestScope.get('revSortDir')}&page=${requestScope.get('current_page')}&search=${requestScope.get('search')}&locale=${currentLocale}">
                    <fmt:message key="movie.title"/>
                </a></th>
                <th scope="col">
                    <a href="?sort=seats_avail&sort_dir=${requestScope.get('revSortDir')}&page=${requestScope.get('current_page')}&search=${requestScope.get('search')}&locale=${currentLocale}">
                        <fmt:message key="free.seats"/>
                    </a></th>
                <c:if test="${'ADMIN'.equalsIgnoreCase(sessionScope.get('user').getRole())}">
                    <th scope="col"><span>Admin</span></th>
                </c:if>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="row" items="${requestScope.get('rows')}">
                <tr>
                    <th scope="row"><c:out value="${row.getDayOfSession()}"/></th>
                    <td><fmt:message key="day-${row.getDayOfSession().getDayOfWeek().getValue()}-pag"/></td>
                    <td><c:out value="${row.getTimeStart()}"/></td>
                    <td class="table-data-title"><a
                            href="${ctxPath}/app/details?id=${row.getId()}"
                    ><c:out value="${currentLocale == 'en' ? row.getMovie().getTitleEn() : row.getMovie().getTitleUa()}"/></a>
                    </td>
                    <td><c:out value="${row.getSeatsAvailable()}"/></td>
                    <c:if test="${'ADMIN'.equalsIgnoreCase(sessionScope.get('user').getRole())}">
                        <td>
                            <form action="${ctxPath}/app/admin/sessions/remove?id=${row.getId()}" method="post">
                                <input type="submit" value="<fmt:message key="remove"/>">
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
            <c:if test="${requestScope.get('pages') > 0}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link"
                               href="?page=${requestScope.get('current_page') > 0 ? requestScope.get('current_page') - 1 : 0}&sort=${requestScope.get('sortParam')}&sort_dir=${requestScope.get('sortDir')}&search=${requestScope.get('search')}&value=${requestScope.get('value')}&locale=${currentLocale}"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach var="page" begin="1" end="${requestScope.get('pages')}">
                            <li class="${requestScope.get('current_page') == page - 1 ? 'page-item active' : 'page-item'}">
                                <a class="page-link"
                                   href="?page=${page - 1}&sort=${requestScope.get('sortParam')}&sort_dir=${requestScope.get('sortDir')}&search=${requestScope.get('search')}&locale=${currentLocale}&value=${requestScope.get('value')}"
                                ><c:out value="${page}"/>
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item">
                            <a class="page-link"
                               href="?page=${requestScope.get('current_page') < requestScope.get('pages') - 1 ? requestScope.get('current_page') + 1 : requestScope.get('pages') - 1}&sort=${requestScope.get('sortParam')}&sort_dir=${requestScope.get('sortDir')}&value=${requestScope.get('value')}&search=${requestScope.get('search')}&locale=${currentLocale}"
                               aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
    </section>
</main>
<%@ include file="./fragments/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>
</html>