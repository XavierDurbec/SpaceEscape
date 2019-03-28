package com.excilys.formation.projet.core;

import com.excilys.formation.projet.core.exception.NoPlayerForThisCharacterException;
import com.excilys.formation.projet.core.model.player.Player;
import com.excilys.formation.projet.core.model.boardMap.*;
import com.excilys.formation.projet.core.model.character.alien.*;
import com.excilys.formation.projet.core.model.character.marine.*;
import com.excilys.formation.projet.core.model.character.Character;
import com.excilys.formation.projet.core.service.GameService;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private static Character[] GAME_CHARACTER = new Character[]{new Lurker(), new Praetorian(), new Soldier(), new Engineer()};

    private Logger logger = Logger.getLogger("Game");
    private String name;
    private BoardMap map;
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private int turn;
    private boolean deficientCapsuleDetected;
    private GameService gameService;

    public Game(String name, BoardMap map, List<Player> activePlayers, GameService gameService) {
        this.name = name;
        this.map = map;
        this.players.addAll(activePlayers);
        this.activePlayers.addAll(players);
        this.gameService = gameService;
    }

    public Game(String name, List<Player> activePlayers, GameService gameService) {
        this(name,new BoardMap(), activePlayers, gameService);
        initGame();
    }

    /**
     * Asigne a unique Character for each player randomly.
     */
    private void initGame() {
        List<Character> characters = new ArrayList<>(Arrays.asList(GAME_CHARACTER));
        players.stream().limit(activePlayers.size() ).forEach(player -> player.setCharacter(randomCharacter(characters)));
    }

    /**
     * Take a Character's list, pick one to return it and remove it from the list.
     * @param characters
     * @return
     */
    private Character randomCharacter(List<Character> characters) {
        Random random = new Random();
        Character character = characters.get(random.nextInt(characters.size()));
        characters.remove(character);
        return character;
    }

    public void play() {
        for (Player player : this.activePlayers){
            if(player.getCharacter() instanceof Alien){
                player.getCharacter().setCoordinate(this.map.getAlienSpawn());
            } else if (player.getCharacter() instanceof Marine){
                player.getCharacter().setCoordinate(this.map.getMarineSpawn());
            }
        }
        do {
            turn++;
            System.out.println("********************* turn " + turn + " begin *********************" );
            for (Player player : activePlayers) {
                if (gameContinue() && player.getCharacter().isAlive() && !player.getCharacter().isEscaped()){
                    playerTurn(player);
                } else {
                    break;
                }
            }
        } while (gameContinue());

        System.out.println("Game is over");
        gameService.diplayWinner(players);

    }


    private boolean gameContinue(){
        for (Player player : this.activePlayers){
            if(player.getCharacter() instanceof Marine){
                Marine marine = (Marine) player.getCharacter();
                if(marine.isAlive() && !marine.isEscaped()){
                    return true;
                }
            }
        }
        return false;
    }

    private void playerTurn (Player player){
        Scanner sc = new Scanner(System.in);
        System.out.println("********************* " + player.getSurname() + " (" + player.getCharacter().getName() + ")" + " turn " + turn + " *********************");
        this.map.displayPlayerMap(player);
        if(player.getCharacter().isCanAtck()){
            System.out.println("Do you want attack or move? (A or M)");
            String entry = sc.next();
            if(entry.equals("A")){
                attack(player);
            } else {
                move(player);
            }
        } else {
            move(player);
        }

    }

    private void attack(Player player) {
        displacement(player);
        List<Placeable> placeableList = this.map.getMap().get(player.getCharacter().getCoordinate()).getPlaceables();
        gameService.attackPing(player);

        for (Placeable placeable : placeableList){
            if(!(placeable instanceof Praetorian) && placeable instanceof Character && !placeable.equals(player.getCharacter())){
                Character character = (Character) placeable;
                character.setAlive(false);
                this.map.getMap().get(player.getCharacter().getCoordinate()).removePlaceble(placeable);
                try{
                    gameService.playerDeath(player, getPlayerOfThisCharacter(character));
                } catch (NoPlayerForThisCharacterException e){
                    logger.log(Level.WARNING, e.getMessage());
                }
            }
        }
        if (player.getCharacter() instanceof Soldier){ // Soldier can attack only once.
            player.getCharacter().setCanAtck(false);
        }
    }

    private Player getPlayerOfThisCharacter(Character character) throws NoPlayerForThisCharacterException{
        for (Player player : this.players) {
            if(player.getCharacter().equals(character)){
                return player;
            }
        }
        throw new NoPlayerForThisCharacterException();
    }
    private void move(Player player){
        displacement(player);
        switch (this.map.getMap().get(player.getCharacter().getCoordinate()).getType()){
            case SAFE:
                gameService.safePing(player);
                break;
            case UNSAFE:
                unsafeRoomDraw(player);
                break;
            case CAPSULE:
                capsuleOpening(player);

        }
    }

    /**
     * On an unsafe room, three things can be randomly done.
     * 1: No luck, player make noise on his position.
     * 2: Player cane make noise where he want on the map
     * 3: Player dont do noise.
     * @param player
     */
    private void unsafeRoomDraw(Player player){
        Random random = new Random();
        int res = random.nextInt(2);
        if(res == 0){
            gameService.noisePing(player, player.getCharacter().getCoordinate());
        } else if(res == 1){
            distantPing(player);
        } else {
            gameService.silentPing(player);
        }
    }


    private void distantPing(Player player){
        List<Coordinate> possibleMove = new ArrayList<>(this.map.getMap().keySet());
        Scanner entry;
        Coordinate choosingRoom = gameService.askPlayerWhereMakeNoise(player, map.getWidth(), map.getHeight());
        gameService.noisePing(player, choosingRoom);
    }

    private void capsuleOpening( Player player){
        if (deficientCapsuleDetected || player.getCharacter() instanceof Engineer) {
            playerEscape(player);
        } else {
            Random random = new Random();
            int detection = random.nextInt(4);
            if(detection == 0){
                System.out.println("Bad luck, this capsule is deficient. Your salvation is in another capsule.");
                this.map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.DEFICIENT_CAPSULE);
                this.deficientCapsuleDetected = true;
            } else {
                playerEscape(player);
            }
        }
    }

    private void playerEscape(Player player){
        System.out.println("Congrate "+ player.getSurname() +"! You escaped from big giant monstruouse alien.");
        player.setWin(true);
        player.getCharacter().setEscaped(true);
    }

    private void displacement(Player player){
         List<Coordinate> possibleMove = new ArrayList<>();
         Character character = player.getCharacter();
        whereCanCharacterGo(this.map, character,character.getCoordinate(), character.getMovement(), possibleMove);
        Coordinate choosingMove = gameService.askPlayerWhereMove(player, possibleMove);

        moveCharactereTo(character,choosingMove);
        
    }

    private void moveCharactereTo(Character character, Coordinate choosingCoordinate){
        this.map.getMap().get(character.getCoordinate()).removePlaceble(character);
        this.map.getMap().get(choosingCoordinate).addPlaceable(character);
        character.setCoordinate(choosingCoordinate);
    }


    private List<Coordinate> whereCanCharacterGo (BoardMap map, Character character, Coordinate coordinate, int distance, List<Coordinate> result){
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
}

