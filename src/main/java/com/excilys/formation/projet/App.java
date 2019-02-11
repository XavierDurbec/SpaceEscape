package com.excilys.formation.projet;

import com.excilys.formation.projet.boardMap.BoardMap;
import com.excilys.formation.projet.character.alien.Praetorian;
import com.excilys.formation.projet.character.marine.Engineer;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BoardMap map = new BoardMap();
        map.displayMap();

        Player omar = new Player("Omar");
        omar.setCharacter(new Engineer());
        Player yan = new Player("Yan");
        yan.setCharacter(new Praetorian());
        List<Player> players = new ArrayList<>();
        players.add(omar);
   //     players.add(yan);

        Game game = new Game("test", players);

        game.play();

    }
}
