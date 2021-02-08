<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- set the locale --%>
<fmt:setLocale value="${param.locale}" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="messages"/>

<%-- set current locale to session --%>
<c:set var="currentLocale" value="${param.locale}" scope="session"/>
<%----%>

<header class="row">
    <div class="col">
    </div>
    <div class="container d-flex justify-content-center col-md schedule-link">
        <c:set var = "salary" scope = "session" value = "${2000*2}"/>
        <c:out value = "${salary}"/>
        <a class="link-to-home" href="/home"><fmt:message key="go.home"/></a>
    </div>
    <div class="col d-flex justify-content-end align-items-center">
        <div class="row d-flex w-100 admin-menu justify-content-center">
            <div  class="admin-access col-md d-flex justify-content-end">
                <a href="/admin"><fmt:message key="go.admin"/></a>
            </div>
        </div>

        <div class="logout-button col-md" sec:authorize="isAuthenticated()">
            <form action="/logout" method="post">
                <button type="submit" class="btn btn-outline-secondary-light text-nowrap mx-2 logout-button">Sign out</button>
            </form>
        </div>
        <div>
            <a href="/login">
                <i class="fas fa-sign-in-alt fa-3x"></i>
            </a>
        </div>
        <div>
            <a href="/cabinet">
                <i class="far fa-user-circle fa-3x"></i>
            </a>
        </div>
        <!--    LANGUAGE -->
        <div class="dropdown mx-2">
        <span class="dropdown-toggle language-control" type="button" id="dropdownMenuButton"
              data-bs-toggle="dropdown" aria-expanded="false" >
            Lang
        </span>
            <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton">
                <li class="mx-0"><a class="dropdown-item" href="${pageContext.request.contextPath}/?locale=en">EN</a></li>
                <li class="mx-0"><a class="dropdown-item" href="${pageContext.request.contextPath}/?locale=ua">UA</a></li>
            </ul>
        </div>
    </div>
</header>
