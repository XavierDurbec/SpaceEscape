package com.excilys.formation.projet;

import com.excilys.formation.projet.boardMap.BoardMap;
import com.excilys.formation.projet.boardMap.Coordinate;
import com.excilys.formation.projet.boardMap.RoomType;
import com.excilys.formation.projet.character.Character;
import com.excilys.formation.projet.character.alien.Alien;
import com.excilys.formation.projet.character.marine.Marine;

import java.sql.SQLOutput;
import java.util.*;

public class Game {

    private String name;
    private BoardMap map;
    private TreeSet<Player> players = new TreeSet<>() ;
    private int turn;

    public Game(String name, BoardMap map, Collection<Player> players) {
        this.name = name;
        this.map = map;
        this.players.addAll(players);
    }

    public Game(String name, Collection<Player> players) {
        this(name,new BoardMap(), players);

    }

    private void playerTurn(Player player){
        System.out.println("********************* " + player.getSurname() + " turn " + turn + " *********************");

        if(player.getCharacter().isCanAtck()){
            System.out.println("Voulez vous attaquer ou vous déplacer? (A or M");
            Scanner sc = new Scanner(System.in);
            if(sc.equals('A')){
               // TODO atk
            } else {
                
            }
        } else {
            // TODO bouger
        }

    }
    
    private void move(Character character){
        move(character);
        switch (this.map.getMap().get(character.getCoordinate()).getType()){
            case SAFE:
                safePing();
                break;
            case UNSAFE:

                break;
            case CAPSUL:
        }
    }

    private void unsafeRoomDraw(Character character){
        Random random = new Random();
        random.nextInt(3);
       // TODO
    }

    private void safePing(){
        System.out.println("Someone enter to a safe room.");
    }

    private void silentPing(){
        System.out.println("**Silent**");
    }
    private void noisePing(Coordinate coordinate){
        System.out.println("There is noise on " + coordinate);
    }

    private void attackPing(Coordinate coordinate){
        System.out.println("There is attack on " + coordinate);
    }

    private void displacement(Character character){
        List<Coordinate> possibleMove = character.whereCanMove();
        Scanner entry;
        Coordinate choosingMove;
        do{
            System.out.println("Where do you want to displacement? ");
            System.out.println(character.whereCanMove());
            entry = new Scanner(System.in);
            choosingMove = parseToCoordinate(entry.next());
        } while (possibleMove.contains(choosingMove));

        moveCharactereTo(character,choosingMove);
        
    }


    private static Coordinate parseToCoordinate(String coordinateString){
        String[] parse = coordinateString.split(":");
        return new Coordinate(Integer.valueOf(parse[0]), Integer.valueOf(parse[1]));// TODO si parse pas bien

    }
    
    private void moveCharactereTo(Character character, Coordinate choosingCoordinate){
        this.map.getMap().get(character.getCoordinate()).removePlaceble(character);
        this.map.getMap().get(choosingCoordinate).addPlaceable(character);
        character.setCoordinate(choosingCoordinate);
    }


    // TODO ultra moche

    /**
     * Détermine les coordonée innacessible et les enléve de la liste.
     * @param coordinateList
     * @param character
     */
    private void removeImpossibleCase(List<Coordinate> coordinateList,Character character){
        for(Coordinate coordinate : coordinateList){
            if(character.getClass().equals(Alien.class)){
                if (this.map.getMap().get(coordinate).getType().equals(RoomType.MARINE_SPAWN) || this.map.getMap().get(coordinate).getType().equals(RoomType.MARINE_SPAWN)){
                    coordinateList.remove(coordinate);
                }
            } else if (character.getClass().equals(Marine.class)){
                if(this.map.getMap().get(coordinate).getType().equals(RoomType.ALIEN_SPAWN){
                    coordinateList.remove(coordinate)
                }
            }

            if (this.map.getMap().get(coordinate).getType().equals(RoomType.CONNDEMNED)){
                coordinateList.remove(coordinate);

            }
        }
    }
}

