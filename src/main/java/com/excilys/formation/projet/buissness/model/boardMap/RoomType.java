package com.excilys.formation.projet.buissness.model.boardMap;

public enum RoomType {
    SAFE("_"), UNSAFE("."), CONDEMNED("*"), MARINE_SPAWN("M"), ALIEN_SPAWN("A"), DEFICIENT_CAPSULE("H"), CAPSULE("O"), USED_CAPSULE("#");

    private String display;

    RoomType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
