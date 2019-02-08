package com.excilys.formation.projet.boardMap;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private RoomType type;
    private List<Placeable> placeables;

    public Room(RoomType type) {
        this.type = type;
        this.placeables = new ArrayList<Placeable>();
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void addPlaceable(Placeable placeable){
        this.placeables.add(placeable);
    }

    public void addPlaceables(List<Placeable> placeables){
        this.placeables.addAll(placeables);
    }

    public void removePlaceble(Placeable placeable){
        this.placeables.remove(placeable);
    }

    public void removePlacebles(List<Placeable> placeables){
        this.placeables.removeAll(placeables);
    }

    public List<Placeable> getPlaceables() {
        return placeables;
    }

    @Override
    public String toString() {
        switch (this.type){
            case SAFE:
                return "_";
            case UNSAFE:
                return ".";
            case CONNDEMNED:
                return "*";
            case CAPSULE:
                return "O";
            case ALIEN_SPAWN:
                return "A";
            case MARINE_SPAWN:
                return "M";
            case USED_CAPSULE:
                return "X";
            case DEFICIENT_CAPSULE:
                return "X";
                default:
                    return "ERROR";
        }
    }
}
