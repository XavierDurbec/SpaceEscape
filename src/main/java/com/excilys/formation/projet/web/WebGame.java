package com.excilys.formation.projet.web;

import com.excilys.formation.projet.buissness.exception.NoPlayerForThisCharacterException;
import com.excilys.formation.projet.buissness.model.player.Player;
import com.excilys.formation.projet.buissness.model.boardMap.*;
import com.excilys.formation.projet.buissness.model.character.alien.*;
import com.excilys.formation.projet.buissness.model.character.marine.*;
import com.excilys.formation.projet.buissness.model.character.Character;
import com.excilys.formation.projet.buissness.service.GameService;

import java.util.*;
import java.util.stream.Collectors;

public class WebGame {
    private static Character[] GAME_CHARACTER = new Character[]{new Lurker(), new Praetorian(), new Soldier(), new Engineer()};

    private String name;
    private BoardMap map;
    private List<Player> players = new ArrayList<>();
    private int activePlayerRank = 0;
    private int turn;
    private boolean deficientCapsuleDetected;
    private static WebGame webGame;

    public static void config(String name, List<Player> players) {
        if (webGame == null) {
            webGame = new WebGame(name, players);
        }
    }

    public static void reset() {
        webGame = null;
    }

    public static WebGame getInstance() {
        return webGame;
    }

    public WebGame(String name, BoardMap map, List<Player> players) {
        this.name = name;
        this.map = map;
        this.players.addAll(players);
        initGame();
    }

    public WebGame(String name, List<Player> players) {
        this(name,new BoardMap(), players);
    }


    private void initGame() {
        List<Character> characters = new ArrayList<>(Arrays.asList(GAME_CHARACTER));
        players.stream()
                .limit(players.size())
                .forEach(
                        player -> {
                            player.setCharacter(randomCharacter(characters));
                            if (player.getCharacter() instanceof Alien) {
                                player.getCharacter().setCoordinate(map.getAlienSpawn());
                            } else {
                                player.getCharacter().setCoordinate(map.getMarineSpawn());
                            }
                        });
    }


    private Character randomCharacter(List<Character> characters) {
        Random random = new Random();
        Character character = characters.get(random.nextInt(characters.size()));
        characters.remove(character);
        return character;
    }

    public List<Player> getWinners(){
        List<Player> winners = players.stream().filter(Player::haveWin).collect(Collectors.toList());
        if(winners.size() == 0){
            return players.stream().filter(player -> player.getCharacter() instanceof Alien).collect(Collectors.toList());
        }
        return winners;
    }

    public boolean gameContinue(){
        for (Player player : this.players){
            if(player.getCharacter() instanceof Marine){
                Marine marine = (Marine) player.getCharacter();
                if(marine.isAlive() && !marine.isEscaped()){
                    return true;
                }
            }
        }
        return false;
    }


    private Player getPlayerOfThisCharacter(Character character) throws NoPlayerForThisCharacterException{
        for (Player player : this.players) {
            if(player.getCharacter().equals(character)){
                return player;
            }
        }
        throw new NoPlayerForThisCharacterException();
    }



    public void moveCharactereTo(Character character, Coordinate choosingCoordinate){
        this.map.getMap().get(character.getCoordinate()).removePlaceble(character);
        this.map.getMap().get(choosingCoordinate).addPlaceable(character);
        character.setCoordinate(choosingCoordinate);
    }

    public List<Coordinate> whereCanCharacterGo (BoardMap map, Character character, Coordinate coordinate, int distance, List<Coordinate> result){
        if(isValideDestination(map, character,coordinate, result)){
            result.add(coordinate);
        }
        if(distance <= 0){
            return result;
        } else {
            List<Coordinate> valideProxyRoomList = giveValideProxyRoomList(character, coordinate, result);
            for (Coordinate valideProxyRoom : valideProxyRoomList){
                whereCanCharacterGo(map, character, valideProxyRoom, distance-1, result);
            }
            return result;
        }

    }

    private List<Coordinate> giveValideProxyRoomList(Character character, Coordinate coordinate, List<Coordinate> result){
        List<Coordinate> valideProxyRoomList = new ArrayList<>();

        Coordinate up = new Coordinate(coordinate.getX(),coordinate.getY()-1);
        Coordinate right = new Coordinate(coordinate.getX()+1,coordinate.getY());
        Coordinate down = new Coordinate(coordinate.getX(),coordinate.getY()+1);
        Coordinate left = new Coordinate(coordinate.getX()-1,coordinate.getY());

        if(isValideDestination(map, character, up, result)){
            valideProxyRoomList.add(up);
        }
        if(isValideDestination(map, character, right, result)){
            valideProxyRoomList.add(right);
        }
        if(isValideDestination(map, character, down, result)){
            valideProxyRoomList.add(down);
        }
        if(isValideDestination(map, character, left, result)){
            valideProxyRoomList.add(left);
        }
        return valideProxyRoomList;
    }

    private boolean isValideDestination(BoardMap map, Character character, Coordinate coordinate, List<Coordinate> result){
        if (coordinate.getX() < 0 ||
                coordinate.getY() < 0 ||
                coordinate.getX() >= map.getWidth() ||
                coordinate.getY() >= map.getHeight() ||
                result.contains(coordinate) ||
                (coordinate.equals(character.getCoordinate()) && !(character instanceof Lurker)) ||
                map.getMap().get(coordinate).getType().equals(RoomType.CONDEMNED) ){
            return false;
        } else {
            return true;
        }
    }

    public List<String> attackAction(Player player) {
        List<Placeable> placeableList = this.map.getMap().get(player.getCharacter().getCoordinate()).getPlaceables();
        List<Player> killedPlayer = new ArrayList<>();
        List<String> retunedString = new ArrayList<>();
        retunedString.add(player.getSurname() + " has attacked room " + player.getCharacter().getCoordinate());
        for (Placeable placeable : placeableList){
            if(!(placeable instanceof Praetorian) && placeable instanceof Character && !placeable.equals(player.getCharacter())){
                Character character = (Character) placeable;
                character.setAlive(false);
                this.map.getMap().get(player.getCharacter().getCoordinate()).removePlaceble(placeable);
                try {
                    killedPlayer.add(getPlayerOfThisCharacter(character));
                } catch (NoPlayerForThisCharacterException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (player.getCharacter() instanceof Soldier){ // Soldier can attack only once.
            player.getCharacter().setCanAtck(false);
        }
        killedPlayer.stream().forEach(playerKilled -> retunedString.add(playerKilled.getSurname() + "(" + player.getCharacter().getName() + ") has been killed by " + player.getSurname()));
        return retunedString;
    }

    public String moveAction(Player player){
        switch (this.map.getMap().get(player.getCharacter().getCoordinate()).getType()){
            case SAFE:
                return player.getSurname() + "enter in a safe room.";
            case UNSAFE:
                return unsafeRoomDraw(player);
            case CAPSULE:
                return capsuleOpening(player);
        }
        return "not possible inside moveAction";
    }

    private String unsafeRoomDraw(Player player){
        Random random = new Random();
        int res = random.nextInt(2);
        if(res == 0){
            return player.getSurname() + " make noise on " + player.getCharacter().getCoordinate();
        } else {
            return player.getSurname() + "enter on an unsafe room but doesn't make noise.";
        }
    }

    private String capsuleOpening( Player player){
        if (deficientCapsuleDetected || player.getCharacter() instanceof Engineer) {
            return playerEscape(player);
        } else {
            Random random = new Random();
            int detection = random.nextInt(4);
            if(detection == 0){

                this.map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.DEFICIENT_CAPSULE);
                this.deficientCapsuleDetected = true;
                return "Bad luck, this capsule is deficient. Your salvation is in another capsule.";
            } else {
                return playerEscape(player);
            }
        }
    }

    private String playerEscape(Player player){
        player.setWin(true);
        player.getCharacter().setEscaped(true);
        return "Congrate "+ player.getSurname() +"! You escaped from big giant monstruouse alien.";
    }


    ///////////
    // Given //
    ///////////

    public Player getActivePlayer(){
        return this.players.get(activePlayerRank);
    }

    public Player getNextPlayer(){
        activePlayerRank++;
        if(activePlayerRank >= players.size()){
            activePlayerRank = 0;
        }
        return getActivePlayer();
    }

    public Player getAndIncrementPlayer(){
        int tmp = activePlayerRank;
        activePlayerRank++;
        if(activePlayerRank >= players.size()){
            activePlayerRank = 0;
        }
        System.out.println("number : " + tmp);
        return this.players.get(tmp);
    }


    public BoardMap getMap(){
        return this.map;
    }

}

