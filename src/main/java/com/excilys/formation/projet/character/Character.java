package com.excilys.formation.projet.character;

import com.excilys.formation.projet.boardMap.Coordinate;
import com.excilys.formation.projet.boardMap.Placeable;

import java.util.ArrayList;
import java.util.List;

public abstract class Character implements Placeable {
        protected int movement;
        protected Coordinate coordinate;
        protected boolean canAttack;
    public Character(int movement) {
        this.movement = movement;
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

    public String getName(){
        return this.getClass().toString();
    }

    public boolean isCanAtck() {
        return canAttack;
    }

    public void setCanAtck(boolean canAtck) {
        this.canAttack = canAtck;
    }

    public List<Coordinate> whereCanMove(){
        List<Coordinate> coordinates = new ArrayList<>();
            for (int x = -this.getMovement() ; x < this.getMovement() ; x++){
                for (int y = -this.getMovement() ; y < this.getMovement() ; y++){
                    coordinates.add(new Coordinate(this.coordinate.getX() + x, this.getCoordinate().getY() + y));
                }
            }
            return coordinates;
    }


}
