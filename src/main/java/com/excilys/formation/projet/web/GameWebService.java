package com.excilys.formation.projet.web;

import com.excilys.formation.projet.buissness.model.boardMap.BoardMap;
import com.excilys.formation.projet.buissness.model.boardMap.Coordinate;
import com.excilys.formation.projet.buissness.model.player.Player;
import com.excilys.formation.projet.buissness.service.GameService;

import java.util.List;

public class GameWebService implements GameService {

    @Override
    public void displayPlayerMap(Player player, BoardMap map) {

    }

    @Override
    public Coordinate wherePlayerWantMove(Player player, List<Coordinate> possibleMove) {
        return null;
    }

    @Override
    public Coordinate wherePlayerWantMakeNoise(Player player, int mapWidth, int mapHeight) {
        return null;
    }

    @Override
    public void safePing(Player player) {

    }

    @Override
    public void silentPing(Player player) {

    }

    @Override
    public void noisePing(Player player, Coordinate coordinate) {

    }

    @Override
    public void attackPing(Player player) {

    }

    @Override
    public void gameIsOver(List<Player> players) {

    }

    @Override
    public void playerDeath(Player killer, Player killedPlayer) {

    }

    @Override
    public void newTurnPing(int turnNumber) {

    }

    @Override
    public void newPlayerTurnPing(Player player, int turn) {

    }

    @Override
    public boolean doesPlayerWantAttack(Player player) {
        return false;
    }

    @Override
    public void playerEscaped(Player player) {
        
    }

    @Override
    public void capsuleUseFailed(Player player) {

    }
}
