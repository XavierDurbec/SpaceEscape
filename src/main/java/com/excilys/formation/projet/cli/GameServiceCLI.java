package com.excilys.formation.projet.cli;

import com.excilys.formation.projet.buissness.exception.BadParseException;
import com.excilys.formation.projet.buissness.model.boardMap.BoardMap;
import com.excilys.formation.projet.buissness.model.boardMap.Coordinate;
import com.excilys.formation.projet.buissness.model.player.Player;
import com.excilys.formation.projet.buissness.service.GameService;
import com.excilys.formation.projet.buissness.model.character.Character;

import java.util.List;
import java.util.Scanner;

public class GameServiceCLI implements GameService {



    @Override
    public void displayPlayerMap(Player player, BoardMap map){
        displayColumn(map);
        for (int y = 0 ; y < map.getHeight() ; y++){
            displayLine(y);
            for(int x = 0 ; x < map.getWidth() ; x++){
                if(player.getCharacter().getCoordinate().equals(new Coordinate(x,y))){
                    System.out.print("X  ");
                } else {
                    System.out.print(map.getMap().get(new Coordinate(x,y)));
                    System.out.print("  ");
                }
            }
            System.out.println();

        }
    }

    @Override
    public Coordinate askPlayerWhereMove(Player player, List<Coordinate> possibleMove) {
        Scanner entry;
        Coordinate choosingMove = null;
        do{
            System.out.println("Where do you want to displacement? you are on: " + player.getCharacter().getCoordinate());
            System.out.println(possibleMove);
            entry = new Scanner(System.in);
            try{
                choosingMove = parseToCoordinate(entry.next());
            } catch (BadParseException e){
                System.out.println("Don't forget ':' between coordinate.");
            } catch (NumberFormatException e){
                System.out.println("Only numbers please.");
            }
        } while (choosingMove == null || !possibleMove.contains(choosingMove));
        return choosingMove;
    }

    @Override
    public Coordinate askPlayerWhereMakeNoise(Player player, int mapHeight, int mapWidth) {
        Scanner entry;
        Coordinate choosingRoom = null;
        do{
            System.out.println("Pick a room where make noise (all map):");
            entry = new Scanner(System.in);
            try{
                choosingRoom = parseToCoordinate(entry.next());
            } catch (BadParseException e){
                System.out.println("Don't forget ':' between coordinate.");
            } catch (NumberFormatException e){
                System.out.println("Only numbers please.");
            }
        } while (choosingRoom == null || choosingRoom.getX() < 0 || choosingRoom.getX() >= mapWidth || choosingRoom.getX() < 0 || choosingRoom.getY() >= mapWidth);
        return choosingRoom;
    }


    public void safePing(Player player){
        System.out.println(player.getSurname() + " enter to a safe room.");
    }

    public void silentPing(Player player){
        System.out.println(player.getSurname() + " don't make a noise.");
    }
    public void noisePing(Player player, Coordinate coordinate){
        System.out.println(player.getSurname() + "make noise on " + coordinate);
    }

    public void attackPing(Player player){
        System.out.println(player.getSurname() + " attack on " + player.getCharacter().getCoordinate());
    }

    @Override
    public void diplayWinner(List<Player> players){;
        players.stream().filter(Player::haveWin).forEach(player -> System.out.println(player.getSurname() + " have win."));
    }

    @Override
    public void playerDeath(Player killer, Player killedPlayer) {
        System.out.println(killedPlayer.getSurname() + " (" + killedPlayer.getCharacter().getName() + ") is kill  by " + killer.getSurname());

    }

    /////////////////////
     // Private Section //
    /////////////////////

    private void displayColumn(BoardMap map){
        System.out.print("   ");
        for (int i = 0 ; i < map.getWidth() ; i++){
            if(i < 10){
                System.out.print(i  + "  ");

            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private void displayLine(int line){
        if(line < 10){
            System.out.print(line + "  ");

        } else {
            System.out.print(line + " ");
        }
    }

    private static Coordinate parseToCoordinate (String coordinateString) throws BadParseException {
        String[] parse = coordinateString.split(":");
        if (parse.length != 2 ){
            throw new BadParseException();
        } else {
            return new Coordinate(Integer.valueOf(parse[0]), Integer.valueOf(parse[1]));
        }
    }



}
