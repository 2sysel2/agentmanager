<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>

        <c:if test="${not empty error}">
            <div style="border: solid 1px red; background-color: yellow; padding: 10px">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <table border="1">
            <thead>
                <tr>
                    <th>Meno</th>
                    <th>Datum narodenia</th>
                    <th>Datum umrtia</th>
                    <th>Level</th>
                    <th colspan="2">Akcie</th>
                </tr>
            </thead>
            <c:forEach items="${agents}" var="agent">
                <tr>
                    <td><c:out value="${agent.name}"/></td>
                    <td><c:out value="${agent.born}"/></td>
                    <td><c:out value="${agent.died}"/></td>
                    <td><c:out value="${agent.level}"/></td>
                    <td><form method="get" action="${pageContext.request.contextPath}/agents"
                              style="margin-bottom: 0;"><input type="hidden" name="id" value="${agent.id}"><input type="submit" value="Upravit"></form></td>
                    <td><form method="post" action="${pageContext.request.contextPath}/agents?action=delete&id=${agent.id}"
                              style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
                </tr>
            </c:forEach>
        </table>

        <h2>Pridanie noveho agenta</h2>
        <form action="${pageContext.request.contextPath}/agents?action=add" method="post">
            <table>
                <tr>
                    <th>Meno</th>
                    <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
                </tr>
                <tr>
                    <th>Datum narodenia</th>
                    <td><input type="date" name="born" value="<c:out value='${param.born}'/>"/></td>
                </tr>
                <tr>
                    <th>Datum umrtia</th>
                    <td><input type="date" name="died" value="<c:out value='${param.died}'/>"/></td>
                </tr>
                <tr>
                    <th>Level</th>
                    <td><input type="text" name="level" value="<c:out value='${param.level}'/>"/></td>
                </tr>
            </table>
            <input type="Submit" value="Pridat" />
        </form>

    </body>
</html>