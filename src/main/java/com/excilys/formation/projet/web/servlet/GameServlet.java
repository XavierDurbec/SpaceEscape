package com.excilys.formation.projet.web.servlet;

import com.excilys.formation.projet.buissness.Game;
import com.excilys.formation.projet.buissness.model.player.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game game;

    public void initGame(String playersName) {
        List<String> playersList = Arrays.asList(playersName.substring(1,playersName.length() - 1).split("\\s*,\\s*"));
        List<Player> players = playersList.stream().map(player -> new Player(player)).collect(Collectors.toList());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (game == null) {
            initGame(request.getParameterValues("players")[0]);
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/View/spaceEscape.jsp").forward(request, response);
    }
}
