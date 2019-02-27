package com.excilys.formation.projet.boardMap;

import com.excilys.formation.projet.character.Character;
import com.excilys.formation.projet.character.alien.Lurker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardMap {
    private final java.util.Map<Coordinate, Room> map = new HashMap<>();
    private final int height;
    private final int width;

    private final Coordinate alienSpawn;
    private final Coordinate marineSpawn;

    public BoardMap(){
        this.height = 15;
        this.width = 21;
        for(int x = 0 ; x < width ; x++){
                for(int y = 0 ; y < height  ; y++){
                    this.map.put(new Coordinate(x,y), new Room(RoomType.UNSAFE));
                }
            }
        this.map.get(new Coordinate(2,2)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(18,12)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(2,12)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(18,2)).setType(RoomType.CAPSULE);

        this.marineSpawn = new Coordinate(10,8);
        this.map.get(marineSpawn).setType(RoomType.MARINE_SPAWN);
        this.alienSpawn = new Coordinate(10,6);
        this.map.get(alienSpawn).setType(RoomType.ALIEN_SPAWN);

        this.map.get(new Coordinate(8,7)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(9,7)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(10,7)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(11,7)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(12,7)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(6,5)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(5,0)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(0,6)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(0,7)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(0,8)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(1,7)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(20,6)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(20,7)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(20,8)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(19,7)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(2,4)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(3,4)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(4,4)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(3,5)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(15,2)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(15,3)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(15,4)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(16,4)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(12,1)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(12,2)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(16,14)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(15,14)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(3,13)).setType(RoomType.CONNDEMNED);


        this.map.get(new Coordinate(5,9)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(5,10)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(6,11)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(7,12)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(16,9)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(16,8)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(15,9)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(15,8)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(14,9)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(14,10)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(15,10)).setType(RoomType.CONNDEMNED);


        this.map.get(new Coordinate(9,10)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(10,10)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(11,10)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(19,8)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(19,9)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(20,9)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(20,10)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(9,3)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(10,3)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(11,3)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(10,11)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(3,10)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(17,10)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(9,14)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(8,14)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(10,14)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(11,14)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(13,14)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(14,14)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(4,13)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(5,13)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(0,9)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(0,10)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(0,11)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(3,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(4,0)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(0,2)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(0,3)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(0,4)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(0,5)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(1,4)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(17,4)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(16,5)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(17,5)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(15,5)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(14,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(15,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(16,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(17,0)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(6,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(7,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(8,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(8,1)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(9,1)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(10,1)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(13,12)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(14,12)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(15,12)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(4,6)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(5,6)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(4,7)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(5,7)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(13,9)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(6,9)).setType(RoomType.SAFE);

        this.map.get(new Coordinate(20,0)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(20,1)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(20,2)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(20,3)).setType(RoomType.SAFE);

    }


    public void displayMap(){
        for (int y = 0 ; y < height ; y++){
            for(int x = 0 ; x < width ; x++){
                System.out.print(this.map.get(new Coordinate(x,y)));
                System.out.print(" ");
            }
            System.out.println();

        }
    }

    public Map<Coordinate, Room> getMap() {
        return map;
    }



    public Coordinate getAlienSpawn() {
        return alienSpawn;
    }

    public Coordinate getMarineSpawn() {
        return marineSpawn;
    }


    public List<Coordinate> whereCanIGo (Character character, Coordinate coordinate, int distance, List<Coordinate> result){
        if(isValideDestination(character,coordinate, result)){
            result.add(coordinate);
        }
        if(distance <= 0){
            return result;
        } else {
            List<Coordinate> valideProxyRoomList = giveValideProxyRoomList(character, coordinate, result);
            for (Coordinate valideProxyRoom : valideProxyRoomList){
                whereCanIGo(character, valideProxyRoom, distance-1, result);
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

        if(isValideDestination(character, up, result)){
            valideProxyRoomList.add(up);
        }
        if(isValideDestination(character, right, result)){
            valideProxyRoomList.add(right);
        }
        if(isValideDestination(character, down, result)){
            valideProxyRoomList.add(down);
        }
        if(isValideDestination(character, left, result)){
            valideProxyRoomList.add(left);
        }
        return valideProxyRoomList;
    }

    private boolean isValideDestination(Character character, Coordinate coordinate, List<Coordinate> result){
        if (coordinate.getX() < 0 ||
                coordinate.getY() < 0 ||
                coordinate.getX() >= this.width ||
                coordinate.getY() >= this.height ||
                result.contains(coordinate) ||
                (coordinate.equals(character.getCoordinate()) && !(character instanceof Lurker)) ||
                this.map.get(coordinate).getType().equals(RoomType.CONNDEMNED) ){
            return false;
        } else {
            return true;
        }
    }
}
