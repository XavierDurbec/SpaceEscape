package com.excilys.formation.projet.buissness;

import com.excilys.formation.projet.buissness.model.player.Player;
import com.excilys.formation.projet.buissness.model.boardMap.*;
import com.excilys.formation.projet.buissness.model.character.alien.*;
import com.excilys.formation.projet.buissness.model.character.marine.*;
import com.excilys.formation.projet.buissness.model.character.Character;
import com.excilys.formation.projet.buissness.exception.BadParseException;

import java.util.*;

public class Game {
    private static Character[] GAME_CHARACTER = new Character[]{new Lurker(), new Praetorian(), new Soldier(), new Engineer()};

    private String name;
    private BoardMap map;
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private int turn;
    private boolean deficientCapsuleDetected;

    public Game(String name, BoardMap map, List<Player> activePlayers) {
        this.name = name;
        this.map = map;
        this.players.addAll(activePlayers);
        this.activePlayers.addAll(players);
    }

    public Game(String name, List<Player> activePlayers) {
        this(name,new BoardMap(), activePlayers);
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
        diplayWinner();

    }

    private void diplayWinner(){;
        players.stream().filter(Player::haveWin).forEach(player -> System.out.println(player.getSurname() + " have win."));
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

    private void attack(Player player){
        displacement(player.getCharacter());
        List<Placeable> placeableList = this.map.getMap().get(player.getCharacter().getCoordinate()).getPlaceables();
        attackPing(player);

        for (Placeable placeable : placeableList){
            if(!(placeable instanceof Praetorian) && placeable instanceof Character && !placeable.equals(player.getCharacter())){
                Character character = (Character) placeable;
                character.setAlive(false);
                this.map.getMap().get(player.getCharacter().getCoordinate()).removePlaceble(placeable);
                System.out.println(character.getName() + " is kill  by " + player.getSurname());
            }
        }
        if (player.getCharacter() instanceof Soldier){ // Soldier can attack only once.
            player.getCharacter().setCanAtck(false);
        }
    }
    
    private void move(Player player){
        displacement(player.getCharacter());
        switch (this.map.getMap().get(player.getCharacter().getCoordinate()).getType()){
            case SAFE:
                safePing(player);
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
            noisePing(player, player.getCharacter().getCoordinate());
        } else if(res == 1){
            distantPing(player);
        } else {
            silentPing(player);
        }
    }
// TODO mettre tous les ping sur les personnages
    private void safePing(Player player){
        System.out.println(player.getSurname() + " enter to a safe room.");
    }

    private void silentPing(Player player){
        System.out.println(player.getSurname() + " don't make a noise.");
    }
    private void noisePing(Player player, Coordinate coordinate){
        System.out.println(player.getSurname() + "make noise on " + coordinate);
    }

    private void attackPing(Player player){
        System.out.println(player.getSurname() + " attack on " + player.getCharacter().getCoordinate());
    }

    private void distantPing(Player player){
        List<Coordinate> possibleMove = new ArrayList<>(this.map.getMap().keySet());
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
        } while (choosingRoom == null || !possibleMove.contains(choosingRoom));
        noisePing(player, choosingRoom);
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

    private void displacement(Character character){
         List<Coordinate> possibleMove = new ArrayList<>();
         this.map.whereCanIGo(character,character.getCoordinate(), character.getMovement(), possibleMove);

        Scanner entry;
        Coordinate choosingMove = null;
        do{
            System.out.println("Where do you want to displacement? you are on: " + character.getCoordinate());
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

        moveCharactereTo(character,choosingMove);
        
    }


    private static Coordinate parseToCoordinate (String coordinateString) throws BadParseException {
        String[] parse = coordinateString.split(":");
        if (parse.length != 2 ){
            throw new BadParseException();
        } else {
            return new Coordinate(Integer.valueOf(parse[0]), Integer.valueOf(parse[1]));
        }
    }

    private void moveCharactereTo(Character character, Coordinate choosingCoordinate){
        this.map.getMap().get(character.getCoordinate()).removePlaceble(character);
        this.map.getMap().get(choosingCoordinate).addPlaceable(character);
        character.setCoordinate(choosingCoordinate);
    }

}

