package com.excilys.formation.projet.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/EscapeGame")
public class GameServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request.getServletPath(); Get uri
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("View/spaceEscape.jsp");
        requestDispatcher.forward(request,response);
    }
}
