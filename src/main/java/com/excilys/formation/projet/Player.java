package com.excilys.formation.projet;

public class Player {
    private final String surname;
    private Character character;

    public Player(String surname) {
        this.surname = surname;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
