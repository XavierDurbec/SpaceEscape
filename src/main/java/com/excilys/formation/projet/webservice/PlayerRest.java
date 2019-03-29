package com.excilys.formation.projet.webservice;

import com.excilys.formation.projet.core.model.player.Player;
import com.excilys.formation.projet.persitence.dao.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerRest {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        return players;

    }

    @GetMapping("/player")
    public List<Player> getPlayers(@RequestParam(value="name", defaultValue = "And") String name) {
        return playerRepository.findBySurname(name);
    }

}

