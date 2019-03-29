package com.excilys.formation.projet;

import com.excilys.formation.projet.core.model.player.Player;
import com.excilys.formation.projet.persitence.dao.PlayerRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 */

@Slf4j
@SpringBootApplication
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Bean
    public CommandLineRunner demo(PlayerRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Player("Jack"));
            repository.save(new Player("Jack"));
            repository.save(new Player("And"));
            repository.save(new Player("Dexter"));
            repository.save(new Player("Dexter"));
            repository.save(new Player("Wesker"));
            // fetch all customers

            log.info("Customers found with findAll():");
            log.info("-------------------------------");

            repository.findAll().forEach(a -> log.debug(a.toString()));

            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L)
                    .ifPresent(player -> {
                        log.info("Player found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(player.toString());
                        log.info("");
                    });

            // fetch customers by last name
            log.info("PLayerr found with findByLastName('Dexter'):");
            log.info("--------------------------------------------");
            repository.findBySurname("Dexter").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}
