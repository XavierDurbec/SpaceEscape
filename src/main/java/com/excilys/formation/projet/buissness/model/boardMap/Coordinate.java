package com.excilys.formation.projet.buissness.model.boardMap;

import java.util.Objects;

public class Coordinate implements Comparable<Coordinate>{
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + this.x + ":" + this.y + "]";
    }

    @Override
    public int compareTo(Coordinate c) {
        return (this.x - c.x == 0) ? this.y - c.y : this.x - c.x;
    }
}
