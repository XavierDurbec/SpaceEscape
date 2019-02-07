package com.excilys.formation.projet;

import com.excilys.formation.projet.character.Character;

public class Player {
    private final String surname;
    private Character character;
    private boolean isAnIA;

    public Player(String surname) {
        this(surname, false);
    }

    public Player(String surname, boolean isAnIA) {
        this.surname = surname;
        this.isAnIA = isAnIA;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isAnIA() {
        return isAnIA;
    }

    public void setAnIA(boolean anIA) {
        isAnIA = anIA;
    }
}
