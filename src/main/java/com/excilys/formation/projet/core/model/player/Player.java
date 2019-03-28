package com.excilys.formation.projet.core.model.player;

import com.excilys.formation.projet.core.model.character.Character;

import java.util.Objects;


public class Player implements Comparable<Player>{
    private final String surname;
    private Character character;
    private boolean isAnIA;
    private boolean haveWin;

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

    public boolean haveWin() {
        return haveWin;
    }

    public void setWin(boolean win) {
        this.haveWin = win;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return surname.equals(player.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname);
    }


    @Override
    public int compareTo(Player o) {
        return 0;
    }
}
