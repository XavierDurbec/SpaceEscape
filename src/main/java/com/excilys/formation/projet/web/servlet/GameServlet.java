package com.excilys.formation.projet.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("EscapeGame")
public class GameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        throws ServletException {
    String action = request.getServletPath();

    try {
        switch (action) {
        case "/new":
            showNewForm(request, response);
            break;
        case "/insert":
            insertFilm(request, response);
            break;
        case "/delete":
            deleteFilm(request, response);
            break;
        case "/edit":
            showEditForm(request, response);
            break;
        case "/update":
            updateFilm(request, response);
            break;
        case "/list":
            listFilm(request, response);
            break;
        }
    } catch (SQLException ex) {
        throw new ServletException(ex);
    }
}
    }
}
