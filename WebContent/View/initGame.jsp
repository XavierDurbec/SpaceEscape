<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Space Escape Init </title>
</head>
<body>
<center>
    <h1>Books Management</h1>
    <h2>
        <a href="/new">Add New Book</a>
        &nbsp;&nbsp;&nbsp;
        <a href="/list">List All Books</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>Liste de films</h2></caption>

        <tr><td>   </td><td> A </td><td> B </td><td> C </td><td> D </td><td> E </td></tr>
        <tr><td> 1 </td><td> * </td><td> x </td><td> * </td><td> * </td><td> * </td></tr>
        <tr><td> 2 </td><td> * </td><td> x </td><td> * </td><td> * </td><td> * </td></tr>
        <tr><td> 3 </td><td> * </td><td> x </td><td> * </td><td> * </td><td> * </td></tr>
        <tr><td> 4 </td><td> * </td><td> x </td><td> * </td><td> * </td><td> * </td></tr>
        <tr><td> <button>*</button> </td><td> * </td><td> x </td><td> * </td><td> * </td><td> * </td></tr>
        <!--     <c:forEach var="film" items="${listFilm}">
                <tr>
                    <td><c:out value="${film.id}" /></td>
                    <td><c:out value="${film.titre}" /></td>
                    <td><c:out value="${film.realisateur}" /></td>
                    <td>
                        <a href="/edit?id=<c:out value='${film.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/delete?id=<c:out value='${film.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach> -->
    </table>
</div>
</body>
</html>