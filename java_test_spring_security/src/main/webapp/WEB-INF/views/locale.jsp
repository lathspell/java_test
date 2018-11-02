<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <body>
        <h2>This is locale.jsp</h2>

        <div>Lang path parameter was: "${lang}"</div>
        <div>Session Variables are: ${sessionScope}</div>

        <c:choose>
            <c:when test="${lang eq 'en'}">
                <c:url var="localeUrl" value="/">
                    <c:param name="locale" value="de" />
                </c:url>
                <a href="${localeUrl}"><spring:message code="switchLang" /></a>
            </c:when>
            <c:otherwise>
                <c:url var="localeUrl" value="/">
                    <c:param name="locale" value="en" />
                </c:url>
                <a href="${localeUrl}"><spring:message code="switchLang" /></a>
            </c:otherwise>
        </c:choose>

    </body>
</html>
