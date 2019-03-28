package com.excilys.formation.projet.core.service;

import com.excilys.formation.projet.core.model.boardMap.BoardMap;
import com.excilys.formation.projet.core.model.boardMap.Coordinate;
import com.excilys.formation.projet.core.model.player.Player;

import java.util.List;

public interface GameService {

    void displayPlayerMap(Player player, BoardMap map);
    Coordinate askPlayerWhereMove(Player player, List<Coordinate> possibleMove);
    Coordinate askPlayerWhereMakeNoise(Player player, int mapWidth, int mapHeight); //TODO comment faire un détrompeur sur Height et Width? Donnée un array? Map?
    void safePing(Player player);
    void silentPing(Player player);
    void noisePing(Player player, Coordinate coordinate);
    void attackPing(Player player);
    void diplayWinner(List<Player> players);
    void playerDeath(Player killer, Player killedPlayer);
}
