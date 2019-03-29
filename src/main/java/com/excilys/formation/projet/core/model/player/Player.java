package com.excilys.formation.projet.core.model.player;

import com.excilys.formation.projet.core.model.character.Character;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
public class Player implements Comparable<Player>, Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    private String surname;

    @Transient
    private Character character;
    @Transient
    private boolean isAnIA;
    @Transient
    private boolean haveWin;

    public  Player (){};

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", character=" + character +
                ", isAnIA=" + isAnIA +
                ", haveWin=" + haveWin +
                '}';
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isHaveWin() {
        return haveWin;
    }

    public void setHaveWin(boolean haveWin) {
        this.haveWin = haveWin;
    }
}
