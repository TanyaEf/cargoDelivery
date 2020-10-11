<%@ page import="com.voroniuk.delivery.Path" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 06.10.2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<fmt:setLocale value="${locale.getLanguage()}"/>

<a href="/controller?command=main"><fmt:message key="all.label.account"/></a>

<h3>Report page</h3>

<form name="city_report" method="get" action="controller">
    <input type="hidden" name="command" value="report">
    <table>
        <tr>
            <td>
                <fmt:message key="main.label.choose_current_city"/>
            </td>
            <td>
                <select name="origin">
                    <option value="0">---<fmt:message key="main.option.any_city"/>---</option>
                    <c:forEach var="city" items="${applicationScope.cities}">
                        <option value="${city.getId()}" ${city.getId()==sessionScope.originId?" selected":" "}>${city.getName(locale)}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="main.label.choose_destination_city"/>
            </td>
            <td>
                <select name="destination">
                    <option value="0">---<fmt:message key="main.option.any_city"/>---</option>
                    <c:forEach var="city" items="${applicationScope.cities}">
                        <option value="${city.getId() }" ${city.getId()==sessionScope.destinationId?" selected":" "}>${city.getName(locale)}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="<fmt:message key="manager.button.report_by_city"/> "/>
            </td>
        </tr>
    </table>
</form>

<form name="day_report" method="get" action="controller">
    <input type="hidden" name="command" value="report">
    <table>
        <tr>
            <td>
                <fmt:message key="report.label.start_date"/>
            </td>
            <td>
                <input type="date" name="start_date">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="report.label.end_date"/>
            </td>
            <td>
                <input type="date" name="end_date">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="<fmt:message key="manager.button.report_by_date"/> "/>
            </td>
        </tr>
    </table>
</form>


<table>
    <tr>
        <th><fmt:message key="all.label.id"/></th>
        <th><fmt:message key="all.label.origin"/></th>
        <th><fmt:message key="all.label.destination"/></th>
        <th><fmt:message key="all.label.adress"/></th>
        <th><fmt:message key="all.label.cargo_type"/></th>
        <th><fmt:message key="all.label.weight"/></th>
        <th><fmt:message key="all.label.volume"/></th>
        <th><fmt:message key="all.label.cost"/></th>
        <th><fmt:message key="all.label.status"/></th>
    </tr>

    <c:forEach var="delivery" items="${deliveries}">
        <tr>
            <td>${delivery.getId()}</td>
            <td>${delivery.getOrigin().getName(locale)}</td>
            <td>${delivery.getDestination().getName(locale)}</td>
            <td>${delivery.getAdress()}</td>
            <td>${delivery.getType().getName(locale)}</td>
            <td>${delivery.getWeight()}</td>
            <td align="right">${delivery.getVolume()}</td>
            <td align="right">${delivery.getCost()}</td>
            <td>${delivery.getLastStatus().getName(locale)}</td>
        </tr>
    </c:forEach>

</table>

<c:if test="${pageNo>2}">
    <a href="<%=Path.COMMAND__MANAGER_ACCOUNT%>&page=1"><fmt:message key="all.href.first"/></a>
    ...
</c:if>

<c:forEach var="i" begin="${pageNo-2>1?pageNo-2:1}" end="${pageNo+2<totalPages?pageNo+2:totalPages}">
    <c:choose>
        <c:when test="${i==pageNo}">
            <c:set var="ref" value="[${i}]"/>
        </c:when>
        <c:otherwise>
            <c:set var="ref" value="${i}"/>
        </c:otherwise>
    </c:choose>
    <a href="<%=Path.COMMAND__MANAGER_ACCOUNT%>&page=${i}">${ref}</a>
</c:forEach>

<c:if test="${totalPages-pageNo>2}">
    ...
    <a href="<%=Path.COMMAND__MANAGER_ACCOUNT%>&page=${totalPages}"> <fmt:message key="all.href.last"/> </a>
</c:if>

</body>
</html>
