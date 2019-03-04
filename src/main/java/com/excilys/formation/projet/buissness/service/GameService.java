package com.excilys.formation.projet.buissness.service;

import com.excilys.formation.projet.buissness.model.boardMap.BoardMap;
import com.excilys.formation.projet.buissness.model.boardMap.Coordinate;
import com.excilys.formation.projet.buissness.model.player.Player;

import com.excilys.formation.projet.buissness.model.character.Character;

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
