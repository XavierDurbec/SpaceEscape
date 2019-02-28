package com.excilys.formation.projet.model.character;

import com.excilys.formation.projet.model.boardMap.Coordinate;
import com.excilys.formation.projet.model.boardMap.Placeable;

public abstract class Character implements Placeable {
    protected int movement;
    protected Coordinate coordinate;
    protected boolean canAttack;
    protected boolean isAlive;
    protected String name;
    protected boolean isEscaped;

    public Character(int movement, String name) {
        this.name = name;
        this.movement = movement;
        isAlive = true;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isCanAtck() {
        return canAttack;
    }

    public void setCanAtck(boolean canAtck) {
        this.canAttack = canAtck;
    }


    public void setEscaped(boolean escaped) {
        isEscaped = escaped;
    }

    public String getName(){ return this.name; }

    public boolean isEscaped(){ return this.isEscaped; }

}
