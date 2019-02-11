package com.excilys.formation.projet.boardMap;

import java.util.HashMap;
import java.util.Map;

public class BoardMap {
    private final java.util.Map<Coordinate, Room> map = new HashMap<>();
    private final int length;
    private final Coordinate alienSpawn;
    private final Coordinate marineSpawn;

    public BoardMap(){
        this.length = 21;
        for(int x = 0 ; x < 21 ; x++){
                for(int y = 0 ; y< 21 ; y++){
                    this.map.put(new Coordinate(x,y), new Room(RoomType.UNSAFE));
                }
            }
        this.map.get(new Coordinate(0,0)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(20,20)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(0,20)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(20,0)).setType(RoomType.CAPSULE);
        this.map.get(new Coordinate(10,9)).setType(RoomType.MARINE_SPAWN);
        this.marineSpawn = new Coordinate(10,9);
        this.map.get(new Coordinate(10,7)).setType(RoomType.CAPSULE);

        this.map.get(new Coordinate(10,11)).setType(RoomType.ALIEN_SPAWN);
        this.alienSpawn = new Coordinate(10,11);
        this.map.get(new Coordinate(8,10)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(9,10)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(10,10)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(11,10)).setType(RoomType.CONNDEMNED);
        this.map.get(new Coordinate(12,10)).setType(RoomType.CONNDEMNED);

        this.map.get(new Coordinate(10,3)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(10,17)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(3,10)).setType(RoomType.SAFE);
        this.map.get(new Coordinate(17,10)).setType(RoomType.SAFE);
    }


    public void displayMap(){
        for (int y = 0 ; y < 21 ; y++){
            for(int x = 0 ; x < 21 ; x++){
                System.out.print(this.map.get(new Coordinate(x,y)));
                System.out.print(" ");
            }
            System.out.println();

        }
    }

    public Map<Coordinate, Room> getMap() {
        return map;
    }

    public int getLength() {
        return length;
    }

    public Coordinate getAlienSpawn() {
        return alienSpawn;
    }

    public Coordinate getMarineSpawn() {
        return marineSpawn;
    }
}
