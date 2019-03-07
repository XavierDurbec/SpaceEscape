<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Space Escape Init </title>
</head>
<body>
<center>

    <form action="/InitGame" method="post">
      <p>Players Name:</p>
        <input name="playerName">
        <input type="submit" value="addPlayer" name="addPlayer">
    </form>
    <table cellpadding="5" border="1">
        <tr>
            <td>Players</td>
        </tr>
        <c:forEach var="player" items="${players}">
            <tr>
                <td><c:out value="${player.surname}"/></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/InitGame/forward">Game</a>

</center>
</body>
</html>