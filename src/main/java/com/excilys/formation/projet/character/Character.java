package com.excilys.formation.projet.character;

import com.excilys.formation.projet.boardMap.Coordinate;
import com.excilys.formation.projet.boardMap.Placeable;

import java.util.ArrayList;
import java.util.List;

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

    public List<Coordinate> whereCanMove(){ //TODO bouger dans board
        List<Coordinate> coordinates = new ArrayList<>();
            for (int x = -this.getMovement() ; x <= this.getMovement() ; x++){
                for (int y = -this.getMovement() ; y <= this.getMovement() ; y++){
                   int movementLength = Math.abs(x) + Math.abs(y); //TODO ajouter contournement terrain impraticable
                    Coordinate newNoordinate =new Coordinate(this.coordinate.getX() + x, this.getCoordinate().getY() + y);
                    if(movementLength <= movement && newNoordinate.getY() >= 0 && newNoordinate.getX() >= 0) { //TODO ajouter condition de sortie de terrain
                       coordinates.add(newNoordinate);
                   }
                }
            }
            return coordinates;
    }

}
