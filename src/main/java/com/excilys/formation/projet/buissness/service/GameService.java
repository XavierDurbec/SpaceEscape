package com.excilys.formation.projet.buissness.service;

import com.excilys.formation.projet.buissness.model.boardMap.BoardMap;
import com.excilys.formation.projet.buissness.model.boardMap.Coordinate;
import com.excilys.formation.projet.buissness.model.player.Player;

import java.util.List;

public interface GameService {

    void displayPlayerMap(Player player, BoardMap map);
    Coordinate askPlayerWhereMove(Player player, List<Coordinate> possibleMove);
    Coordinate askPlayerWhereMakeNoise(int mapHeight, int mapWidth);
    void safePing(Player player);
    void silentPing(Player player);
    void noisePing(Player player, Coordinate coordinate);
    void attackPing(Player player);
    void diplayWinner();
}
