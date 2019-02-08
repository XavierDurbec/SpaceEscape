package com.excilys.formation.projet;

import com.excilys.formation.projet.boardMap.BoardMap;
import com.excilys.formation.projet.boardMap.Coordinate;
import com.excilys.formation.projet.boardMap.Placeable;
import com.excilys.formation.projet.boardMap.RoomType;
import com.excilys.formation.projet.character.Character;
import com.excilys.formation.projet.character.alien.Alien;
import com.excilys.formation.projet.character.alien.Praetorian;
import com.excilys.formation.projet.character.marine.Engineer;
import com.excilys.formation.projet.character.marine.Marine;

import java.util.*;

public class Game {

    private String name;
    private BoardMap map;
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private int turn;
    private boolean deficientCapsuleDetected;

    public Game(String name, BoardMap map, Collection<Player> activePlayers) {
        this.name = name;
        this.map = map;
        this.players.addAll(activePlayers);
        this.activePlayers.addAll(players);
    }

    public Game(String name, Collection<Player> activePlayers) {
        this(name,new BoardMap(), activePlayers);

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
                playerTurn(player);
                if(!player.getCharacter().isAlive() || player.haveWin()){
                    this.activePlayers.remove(player);
                }
            }
        } while (gameOver());
        System.out.println("Game is over");

    }

    private boolean gameOver(){
        for (Player player : this.activePlayers){
            if(player.getCharacter() instanceof Marine){
                return true;
            }
        }
        return false;
    }

    private void playerTurn (Player player){
        System.out.println("********************* " + player.getSurname() + " turn " + turn + " *********************");
        if(player.getCharacter().isCanAtck()){
            System.out.println("Voulez vous attaquer ou vous déplacer? (A or M");
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();
            if(entry.equals('A')){
                attack(player);
            } else {
                move(player);
            }
        } else {
            move(player);
        }

    }

    private void attack(Player player){
        move(player);
        List<Placeable> placeableList = this.map.getMap().get(player.getCharacter().getCoordinate()).getPlaceables();
        for (Placeable placeable : placeableList){
            if(!(placeable instanceof Praetorian) && placeable instanceof Character){
                Character character = (Character) placeable;
                character.setAlive(false);
                this.map.getMap().get(player.getCharacter().getCoordinate()).removePlaceble(placeable);
            }
        }
        attackPing(player.getCharacter().getCoordinate());
    }
    
    private void move(Player player){
        displacement(player.getCharacter());
        switch (this.map.getMap().get(player.getCharacter().getCoordinate()).getType()){
            case SAFE:
                safePing();
                break;
            case UNSAFE:
                unsafeRoomDraw(player.getCharacter());
                break;
            case CAPSULE:
                capsuleOpening(player);

        }
    }

    /**
     * On an unsafe room, three things can be randomly done.
     * 1: No luck, player make noise on his position.
     * 2: Player cane make noise where he want on the map
     * 3: Player dont do noise. TODO: add equipment draw
     * @param character
     */
    private void unsafeRoomDraw(Character character){
        Random random = new Random();
        int res = random.nextInt(2);
        if(res == 0){
            noisePing(character.getCoordinate());
        } else if(res == 1){
            distantPing();
        } else {
            silentPing();
        }
    }
// TODO mettre tous les ping sur les personnages
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

    private void distantPing(){
        List<Coordinate> possibleMove = new ArrayList<>(this.map.getMap().keySet());
        Scanner entry;
        Coordinate choosingRoom;
        do{
            System.out.println("Pick a room where make noise (all map):");
            entry = new Scanner(System.in);
            choosingRoom = parseToCoordinate(entry.next());
        } while (!possibleMove.contains(choosingRoom));
        System.out.println("There is noise on " + choosingRoom);
    }


    private void capsuleOpening( Player player){
        if (deficientCapsuleDetected || player.getCharacter() instanceof Engineer) {
            System.out.println("Congrate! You escaped from big giant monstruouse alien.");
            player.setWin(true);
            player.setCharacter(null);
        } else {
            Random random = new Random();
            int detection = random.nextInt(4);
            if(detection == 0){
                System.out.println("Bad luck, this capsule is deficient. Your salvation is in another capsule.");
                this.map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.DEFICIENT_CAPSULE);
                this.deficientCapsuleDetected = true;
            }
        }
    }

    private void displacement(Character character){
        List<Coordinate> possibleMove = character.whereCanMove();
        possibleMove = removeImpossibleCase(possibleMove, character);
        Scanner entry;
        Coordinate choosingMove;
        do{
            System.out.println("Where do you want to displacement? you are on: " + character.getCoordinate());
            System.out.println(possibleMove);
            entry = new Scanner(System.in);
            choosingMove = parseToCoordinate(entry.next());
        } while (!possibleMove.contains(choosingMove));

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
     * Détermine les coordonée innacessible et les enlévent de la liste.
     * @param coordinateList
     * @param character
     */
    private List<Coordinate> removeImpossibleCase(List<Coordinate> coordinateList,Character character){
        List<Coordinate> cleanedCoordinateList = new ArrayList<>();
        for(Coordinate coordinate : coordinateList){
            if (!this.map.getMap().get(coordinate).getType().equals(RoomType.CONNDEMNED)
                    && !this.map.getMap().get(coordinate).getType().equals(RoomType.DEFICIENT_CAPSULE)
                    && !this.map.getMap().get(coordinate).getType().equals(RoomType.USED_CAPSULE)
                    && !coordinate.equals(character.getCoordinate())) {
                if (character instanceof Alien) {
                    if (!(this.map.getMap().get(coordinate).getType().equals(RoomType.MARINE_SPAWN)
                            && !this.map.getMap().get(coordinate).getType().equals(RoomType.CAPSULE))) {
                       cleanedCoordinateList.add(coordinate);
                    }
                } else if (character instanceof Marine) {
                    if (!this.map.getMap().get(coordinate).getType().equals(RoomType.ALIEN_SPAWN)) {
                        cleanedCoordinateList.add(coordinate);
                    }
                }
            }

        }
        return cleanedCoordinateList;
    }
}

