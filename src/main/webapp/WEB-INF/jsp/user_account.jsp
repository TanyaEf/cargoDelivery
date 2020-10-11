<%@ page import="com.voroniuk.delivery.Path" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<fmt:setLocale value="${locale.getLanguage()}"/>

<a href="/controller?command=main"><fmt:message key="account.anchor.main_page"/></a>

<p>User: ${sessionScope.user.getLogin()}</p>
<p>Locale : ${sessionScope.locale.getLanguage()}</p>
<h3>Create new delivery:</h3>
<form name="order" action="/controller" method="post">
    <input type="hidden" name="command" value="makeOrder">
    <table>
        <tr>
            <td width="200">
                <fmt:message key="main.label.choose_current_city"/>
            </td>
            <td>
                <input type="text" size="50" list="cityList" name="current"
                       value="${lastCurrent==null?sessionScope.currentCity.getName(locale):lastCurrent.getName(locale)}">
                <!--
                <select name="current">
                    <c:forEach var="city" items="${applicationScope.cities}">
                        <option value="${city.getId()}" ${city.getId()==sessionScope.currentCity.getId()?" selected":""}>${city.getName(locale)}</option>
                    </c:forEach>
                </select>
                -->
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="main.label.choose_destination_city"/>
            </td>
            <td>
                <input type="text" size="50" list="cityList" name="cityInp" value="${destination.getName(locale)}">
                <datalist id="cityList">
                    <c:forEach var="city" items="${applicationScope.cities}">
                        <option>${city.getName(locale)}</option>
                    </c:forEach>
                </datalist>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.adress"/>
            </td>
            <td>
                <input type="text" size="50" name="adress" value="${adress}">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.cargo_type"/>
            </td>
            <td>
                <select name="type">
                    <c:forEach var="type" items="${applicationScope.cargoTypes}">
                        <option ${cType==type?" selected":""} value="${type.getId()}">${type.getName(locale)}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.weight"/>
            </td>
            <td>
                <input type="number" size="5" name="weight" value="${weight}">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.length"/>
            </td>
            <td>
                <input type="number" size="5" name="length" value="${length}">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.width"/>
            </td>
            <td>
                <input type="number" size="5" name="width" value="${width}">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.height"/>
            </td>
            <td>
                <input type="number" size="5" name="height" value="${height}">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="all.label.cost"/>
            </td>
            <td>
                ${cost}
            </td>
        </tr>
    </table>
    <input type="submit" name="order" value="<fmt:message key="user.button.make_order"/>"/>
    <input type="submit" name="calculate" value="<fmt:message key="main.button.calculate"/>"/>
</form>
<hr>

<form name="filter" method="get" action="controller">
    <input type="hidden" name="command" value="user_account">
    <table>
        <tr>
            <td>
                <fmt:message key="all.label.status"/>
            </td>
            <td>
                <select name="status">
                    <c:forEach var="status" items="${applicationScope.statuses}">
                        <option value="${status.getId()}" ${status.getId()==sessionScope.status.getId()?" selected" : ""} >${status.getName(locale)}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="submit" value="<fmt:message key="manager.button.filter"/> "/>
            </td>
        </tr>
    </table>
</form>

<h4>Delivery list:</h4>

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
            <td>${delivery.getVolume()}</td>
            <td>${delivery.getCost()}</td>
            <td>${delivery.getLastStatus().getName(locale)}</td>
            <td><c:if test="${delivery.getLastStatus().getId() == 2}">
                <a href="/?command=pay&delivery_id=${delivery.getId()}"><fmt:message key="all.href.pay"/></a>
            </c:if></td>
        </tr>
    </c:forEach>

</table>


<c:if test="${pageNo>2}">
    <a href="<%=Path.COMMAND__USER_ACCOUNT%>&page=1"><fmt:message key="all.href.first"/></a>
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
    <a href="<%=Path.COMMAND__USER_ACCOUNT%>&page=${i}">${ref}</a>
</c:forEach>

<c:if test="${totalPages-pageNo>2}">
    ...
    <a href="<%=Path.COMMAND__USER_ACCOUNT%>&page=${totalPages}"> <fmt:message key="all.href.last"/> </a>
</c:if>

</body>
</html>
