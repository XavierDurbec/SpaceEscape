package com.excilys.formation.projet.persitence.dao;

import com.excilys.formation.projet.core.model.player.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findBySurname(String surname);
}
