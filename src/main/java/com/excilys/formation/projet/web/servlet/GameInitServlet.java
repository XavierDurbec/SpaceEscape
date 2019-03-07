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

@WebServlet({"/InitGame", "/"})
public class GameInitServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private List<Player> players = new ArrayList<>();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    players.add(new Player(request.getParameter("playerName")));
    response.sendRedirect("/InitGame");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println(request.getServletPath());
    if ("/forward".equals(request.getServletPath())) {
      System.out.println("ddz");
      RequestDispatcher rd = request.getRequestDispatcher("View/spaceEspace.jsp");
      request.setAttribute("players", players);
      rd.forward(request, response);
    } else {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("View/initGame.jsp");
      request.setAttribute("players", players);
      requestDispatcher.forward(request, response);
    }
  }
}
