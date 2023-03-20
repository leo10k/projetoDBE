package br.com.fiap.projetodbe.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.models.Game;

@RestController
public class GameController {
    

    Logger log = LoggerFactory.getLogger(UserController.class);

    List<Game> games = new ArrayList<>();
    
    @GetMapping("api/games")
    public List<Game> index() {
        return games;        
    }

    @PostMapping("/api/games")
    public ResponseEntity<Game> create(@RequestBody Game game) {
        log.info("cadastrando o usuario: " + game);
        game.setId(games.size() + 1l);
        games.add(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping("/api/games/{id}")
    public ResponseEntity<Game> show(@PathVariable Long id) {
        log.info("buscando usuario com id: " + id );
        var gameEncontrado = games.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (gameEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(gameEncontrado.get());
    }

    @DeleteMapping("/api/games/{id}")
    public ResponseEntity<Game> destroy(@PathVariable Long id) {
        log.info("apagando usuario com id " + id);
        var gameEncontrado = games.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (gameEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        games.remove(gameEncontrado.get());
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/api/games/{id}")
    public ResponseEntity<Game> update(@PathVariable Long id, @RequestBody Game game){
        log.info("alterando usuario com id " + id);
        var gameEncontrado = games.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (gameEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        games.remove(gameEncontrado.get());
        game.setId(id);
        games.add(game);
        
        return ResponseEntity.ok(game);

    }

}
