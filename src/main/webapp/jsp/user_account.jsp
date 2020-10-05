<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<datalist id="cityList">
    <c:forEach var="city" items="${applicationScope.cities}">
        <option value="${city.getId()}"}>${city.getName(locale)}</option>
    </c:forEach>
</datalist>
<h4>User: ${sessionScope.user.getLogin()}</h4>
<h4>Session id: ${pageContext.session.id}</h4>
<h4>Locale : ${sessionScope.locale.getLanguage()}</h4>
<h3>Create new delivery:</h3>

<form name="order" action="/controller" method="post">
    <input type="hidden" name="command" value="makeOrder">
    <table>
        <tr>
            <td>
                Current city:
            </td>
            <td>
                <select name="current">
                    <c:forEach var="city" items="${applicationScope.cities}">
                        <option value="${city.getId()}" ${city.getId()==sessionScope.currentCity.getId()?" selected":""}>${city.getName(locale)}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Destination city:
            </td>
            <td>
                <input type="text" size="50" list="cityList" name="cityInp" value="${destination.getId()}">
            </td>
        </tr>
        <tr>
            <td>
                Adress:
            </td>
            <td>
                <input type="text" size="50" name="adress" value="${adress}">
            </td>
        </tr>
        <tr>
            <td>Cargo type: </td>
            <td>
                <select name="type">
                    <c:forEach var="type" items="${applicationScope.cargoTypes}">
                        <option ${cType==type?" selected":""}>${type.name()}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Weight:
            </td>
            <td>
                <input type="number" size="5" name="weight" value="${weight}"> kg
            </td>
        </tr>
        <tr>
            <td>
                Length:
            </td>
            <td>
                <input type="number" size="5" name="length" value="${length}"> cm
            </td>
        </tr>
        <tr>
            <td>
                Width:
            </td>
            <td>
                <input type="number" size="5" name="width" value="${width}"> cm
            </td>
        </tr>
        <tr>
            <td>
                Height:
            </td>
            <td>
                <input type="number" size="5" name="height" value="${height}"> cm
            </td>
        </tr>
        <tr>
            <td>
                Cost:
            </td>
            <td>
                ${cost}
            </td>
        </tr>
    </table>
    <input type="submit" name="order" value="make order">
    <input type="submit" name="calculate" value="calculate">

</form>

</body>
</html>
