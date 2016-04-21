<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>
        <h2>Uprava agenta</h2>
        <form action="${pageContext.request.contextPath}/agents?action=edit" method="post">
            <input type="hidden" name="id" value="<c:out value='${agent.id}'/>" />
            <table>
                <tr>
                    <th>Meno</th>
                    <td><input type="text" name="name" value="<c:out value='${agent.name}'/>"/></td>
                </tr>
                <tr>
                    <th>Datum narodenia</th>
                    <td><input type="date" name="born" value="<c:out value='${agent.born}'/>"/></td>
                </tr>
                <tr>
                    <th>Datum umrtia</th>
                    <td><input type="date" name="died" value="<c:out value='${agent.died}'/>"/></td>
                </tr>
                <tr>
                    <th>Level</th>
                    <td><input type="text" name="level" value="<c:out value='${agent.level}'/>"/></td>
                </tr>
            </table>
            <input type="Submit" value="Zmenit" />
        </form>

    </body>
</html>