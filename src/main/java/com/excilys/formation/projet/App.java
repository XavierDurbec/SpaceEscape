package com.excilys.formation.projet;

import com.excilys.formation.projet.boardMap.BoardMap;
import com.excilys.formation.projet.character.alien.Lurker;
import com.excilys.formation.projet.character.alien.Praetorian;
import com.excilys.formation.projet.character.marine.Engineer;
import com.excilys.formation.projet.character.marine.Soldier;

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
       // omar.setCharacter(new Engineer());
        Player yan = new Player("Yan");
        //yan.setCharacter(new Praetorian());
        Player rached = new Player("Rached");
       // rached.setCharacter(new Lurker());
        Player sylvain = new Player("Sylvain");
        //sylvain.setCharacter(new Soldier());
        List<Player> players = new ArrayList<>();
        players.add(omar);
        players.add(yan);
        players.add(rached);
        players.add(sylvain);

        Game game = new Game("test", players);

        game.play();

    }
}
