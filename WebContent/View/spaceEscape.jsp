<%@ page import="com.excilys.formation.projet.buissness.model.boardMap.Coordinate" %>
<%@ page import="com.excilys.formation.projet.buissness.model.boardMap.Room" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>SpaceEscape</title>
</head>
<body>
<center>
<div align="center">
    <table border="1" cellpadding="5" style=" border-collapse: collapse;
  border: 2px solid rgb(200,200,200);
  letter-spacing: 1px;
  font-size: 0.8rem;">

        <c:set value="0" var="entry_number"/>

        <c:forEach items="${board}" var="entry">
            <c:if test="${entry_number == 0}">
                <tr>
            </c:if>

            <c:set var="playerCase" value="${false}"/>
            <c:set var="choiceOk" value="${false}"/>

            <c:if test="${entry.key.x == playerPlace.x && entry.key.y == playerPlace.y}">
                <td>[X]</td>
                <c:set var="playerCase" value="${true}"/>
            </c:if>

            <c:if test="${playerCase == false && empty chooseAction && empty actionDone}">
                <c:forEach items="${possibilities}" var="choice">
                    <c:if test="${entry.key.x == choice.x && entry.key.y == choice.y}">
                        <form action="/game/interaction" method="post">
                            <input type="text" name="moveTo" value="${choice.x}/${choice.y}" hidden>
                            <td><input type="submit" value="Go"></td>
                        </form>
                        <c:set var="choiceOk" value="${true}"/>
                    </c:if>
                </c:forEach>
            </c:if>


            <c:if test="${playerCase == false && choiceOk == false}">
                <td>${entry.value}</td>
            </c:if>

            <c:set var="entry_number" value="${entry_number + 1}"/>

            <c:if test="${height == entry_number}">
                <c:set var="entry_number" value="0"/>
                </tr>
            </c:if>

            <c:set var="playerCase" value="${false}"/>
            <c:set var="choiceOk" value="${false}"/>
        </c:forEach>
    </table>
</div>
<c:forEach items="${messages}" var="messageEntry">
    <c:forEach items="${messageEntry}" var="message">
        <p><c:out value="${message}"/></p>
    </c:forEach>
</c:forEach>
<c:if test="${chooseAction == true && actionDone == false}">
    <form action="/game/interaction" method="post">
        <input type="text" value="move" name="action" hidden>
        <input type="submit" value="Move"/>
    </form>
    <form action="/game/interaction" method="post">
        <input type="text" value="attack" name="action" hidden>
        <input type="submit" value="Attack"/>
    </form>
</c:if>
<c:if test="${actionDone == true}">
        <form action="/game/interaction/tampon" method="get">
            <input type="submit" value="Fin du tour"/>
        </form>
</c:if>
</center>
</body>
</html>