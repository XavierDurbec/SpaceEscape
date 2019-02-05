package com.excilys.formation.projet.character;

import com.excilys.formation.projet.boardMap.Placeable;

public abstract class Character implements Placeable {
        private String name;
        private int movement;

    public Character(String name, int movement) {
        this.name = name;
        this.movement = movement;
    }
}
