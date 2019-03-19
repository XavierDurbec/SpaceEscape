package com.excilys.formation.projet.web.servlet;

import com.excilys.formation.projet.buissness.model.boardMap.Coordinate;
import com.excilys.formation.projet.web.WebGame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InteractionServlet extends HttpServlet {

    private List<List<String>> messages = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/tampon".equals(request.getPathInfo())) {
            WebGame.getInstance().getNextPlayer();
            //response.sendRedirect("/game/start");
            request.setAttribute("messages", messages);
            this.getServletContext().getRequestDispatcher("/game/start").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebGame webGame = WebGame.getInstance();
        if (request.getParameter("moveTo") == null) {
            String action = request.getParameter("action");
            if ("move".equals(action)) {
                System.out.println(webGame.moveAction(webGame.getActivePlayer()));
                messages.add(Arrays.asList(webGame.moveAction(webGame.getActivePlayer())));
            } else if("attack".equals(action)) {
                messages.add(webGame.attackAction(webGame.getActivePlayer()));
            }
            request.setAttribute("actionDone", true);
        } else {
            String place = request.getParameter("moveTo");
            List<Integer> list = Arrays.asList(place.split("/")).stream().map(coordonnee -> Integer.parseInt(coordonnee)).collect(Collectors.toList());
            webGame.moveCharactereTo(webGame.getActivePlayer().getCharacter(), new Coordinate(list.get(0), list.get(1)));
            request.setAttribute("actionDone", false);
            request.setAttribute("chooseAction", true);
        }
        request.setAttribute("messages", messages);
        this.getServletContext().getRequestDispatcher("/game/start/display").forward(request, response);
    }
}
