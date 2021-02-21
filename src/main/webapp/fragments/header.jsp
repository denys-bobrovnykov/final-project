<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- set the locale --%>
<c:set var="currentLocale" value="${not empty param.locale ? param.locale : not empty currentLocale ? currentLocale : 'ua'}" scope="session" />
<fmt:setLocale value="${currentLocale}" />

<fmt:setBundle basename="messages"/>

<%-- set current locale to session --%>
<c:set var="currentUri" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="page"/>
<%----%>

<header class="row">
    <div class="col">
    </div>
    <div class="container d-flex justify-content-center col-md schedule-link">
        <a class="${currentUri.contains('home') ? 'link-to-home marked' : 'link-to-home'}" href="${ctxPath}/app/home"><fmt:message key="go.home"/></a>
    </div>
    <div class="col d-flex justify-content-end align-items-center">
        <c:if test="${'ADMIN'.equalsIgnoreCase(sessionScope.get('user').getRole())}">
            <div class="row d-flex w-100 admin-menu justify-content-center">
                <div  class="admin-access col-md d-flex justify-content-end">
                    <a href="${ctxPath}/app/admin"><fmt:message key="go.admin"/></a>
                </div>
            </div>
        </c:if>
        <div class="logout-button col-md d-flex justify-content-end align-items-center">
            <c:if test="${sessionScope.get('user') != null}">
                <form action="${ctxPath}/app/logout" method="post" class="d-flex align-items-center my-0">
                    <button type="submit" class="btn btn-outline-secondary-light text-nowrap mx-2 logout-button"><fmt:message key="sign.out" /></button>
                </form>
            </c:if>
        </div>
        <c:if test="${sessionScope.get('user') == null}">
            <div>
                <a href="${ctxPath}/app/login">
                    <i class="fas fa-sign-in-alt fa-3x"></i>
                </a>
            </div>
        </c:if>
        <c:if test="${sessionScope.get('user') != null}">
        <div>
            <a href="${ctxPath}/app/cabinet">
                <i class="far fa-user-circle fa-3x"></i>
            </a>
        </div>
        </c:if>
        <!--    LANGUAGE -->
        <div class="dropdown mx-2">
        <span class="dropdown-toggle language-control" type="button" id="dropdownMenuButton"
              data-bs-toggle="dropdown" aria-expanded="false" ><fmt:message key="lang.change"/></span>
            <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton">
                <li class="mx-0"><a class="dropdown-item" href="${currentUri}?locale=en&id=${requestScope.get('selectedSession').getId()}">EN</a></li>
                <li class="mx-0"><a class="dropdown-item" href="${currentUri}?locale=ua&id=${requestScope.get('selectedSession').getId()}">UA</a></li>
            </ul>
        </div>
    </div>
</header>
