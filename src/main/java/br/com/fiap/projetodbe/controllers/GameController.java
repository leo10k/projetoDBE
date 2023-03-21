package br.com.fiap.projetodbe.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.models.Game;
import br.com.fiap.projetodbe.repository.GameRepository;

@RestController
@RequestMapping("/api/games")
public class GameController {
    
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    GameRepository repository;
    
    @GetMapping
    public List<Game> index() {
        return repository.findAll();        
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {
        log.info("cadastrando o usuario: " + game);

        repository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> show(@PathVariable Long id) {
        log.info("buscando usuario com id: " + id );
        var gameEncontrado = repository.findById(id);

        if (gameEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(gameEncontrado.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> destroy(@PathVariable Long id) {
        log.info("apagando usuario com id " + id);
        var gameEncontrado = repository.findById(id);
        if (gameEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        repository.delete(gameEncontrado.get());
        
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> update(@PathVariable Long id, @RequestBody Game game){
        log.info("alterando usuario com id " + id);
        var gameEncontrado = repository.findById(id);

        if (gameEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        game.setId(id);
        repository.save(game);
        
        return ResponseEntity.ok(game);

    }

}