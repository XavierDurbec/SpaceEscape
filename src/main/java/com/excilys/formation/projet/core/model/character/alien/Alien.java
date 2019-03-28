package com.excilys.formation.projet.core.model.character.alien;

import com.excilys.formation.projet.core.model.character.Character;

public abstract class Alien extends Character {

    public Alien(String name) {
        super(2, name);
        this.canAttack = true;

    }
}
