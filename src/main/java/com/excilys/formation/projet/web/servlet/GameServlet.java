package com.excilys.formation.projet.web.servlet;

import com.excilys.formation.projet.buissness.model.boardMap.Coordinate;
import com.excilys.formation.projet.buissness.model.boardMap.Room;
import com.excilys.formation.projet.buissness.model.player.Player;
import com.excilys.formation.projet.web.WebGame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private List<List<String>> messages = new ArrayList<>();

    public void initGame(String playersName) {
        List<String> playersList = Arrays.asList(playersName.substring(1,playersName.length() - 1).split("\\s*,\\s*"));
        List<Player> players = playersList.stream().map(player -> new Player(player)).collect(Collectors.toList());
        WebGame.config("test", players);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameterValues("players") != null) {
            initGame(request.getParameterValues("players")[0]);
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebGame webGame = WebGame.getInstance();
        if ("/display".equals(request.getPathInfo())) {
            if(request.getAttribute("messages") != null) {
                messages = (List<List<String>>) request.getAttribute("messages");
            }
            displayBoard(request,response);
        } else {
            if(webGame.gameContinue()) {
                if(request.getAttribute("messages") != null) {
                    messages = (List<List<String>>) request.getAttribute("messages");
                }
                transitionPlayer(request, response);
            } else {
                request.setAttribute("Winners" , webGame.getWinners());
                this.getServletContext().getRequestDispatcher("/View/winners.jsp").forward(request, response);
            }
        }
    }

    public void displayBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebGame webGame = WebGame.getInstance();
        TreeMap<Coordinate, Room> treeMap = new TreeMap<>();
        treeMap.putAll(webGame.getMap().getMap());
        request.setAttribute("board", treeMap);
        request.setAttribute("playerPlace", webGame.getActivePlayer().getCharacter().getCoordinate());
        request.setAttribute("playerName", webGame.getActivePlayer().getCharacter().getName());
        List<Coordinate> possibilities = webGame.whereCanCharacterGo(webGame.getMap(), webGame.getActivePlayer().getCharacter(),
                webGame.getActivePlayer().getCharacter().getCoordinate(), webGame.getActivePlayer().getCharacter().getMovement(), new ArrayList<>());
        request.setAttribute("possibilities", possibilities);
        request.setAttribute("width", webGame.getMap().getWidth());
        request.setAttribute("height", webGame.getMap().getHeight());
        request.setAttribute("messages", messages);
        this.getServletContext().getRequestDispatcher("/View/spaceEscape.jsp").forward(request, response);
    }

    public void transitionPlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebGame webGame = WebGame.getInstance();
        System.out.println("Begin : " + webGame.getActivePlayer().getCharacter().getCoordinate());
        request.setAttribute("player", webGame.getActivePlayer());
        this.getServletContext().getRequestDispatcher("/View/transition.jsp").forward(request, response);
    }
}
