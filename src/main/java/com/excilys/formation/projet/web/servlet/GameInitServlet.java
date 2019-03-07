package com.excilys.formation.projet.web.servlet;

import com.excilys.formation.projet.buissness.model.player.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GameInitServlet")
public class GameInitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<String> players = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        players.add(request.getParameter("playerName"));
        response.sendRedirect("/game");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("players", players);
        this.getServletContext().getRequestDispatcher("/View/initGame.jsp").forward(request, response);
    }
}
