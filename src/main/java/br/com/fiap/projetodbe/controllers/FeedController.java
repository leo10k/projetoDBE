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

import br.com.fiap.projetodbe.models.Feed;
import br.com.fiap.projetodbe.repository.FeedRepository;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    FeedRepository repository; //IoD

    @GetMapping
    public List<Feed> index(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Feed> create(@RequestBody Feed feed) {
        log.info("cadastrando o usuario: " + feed);
        
        repository.save(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body(feed);
    }

    @GetMapping("{id}")
    public ResponseEntity<Feed> show(@PathVariable Long id) {
        log.info("buscando usuario com id: " + id );
        var feedEncontrado = repository.findById(id);

        if (feedEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(feedEncontrado.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Feed> destroy(@PathVariable Long id) {
        log.info("apagando usuario com id " + id);
        var feedEncontrado = repository.findById(id);

        if (feedEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        repository.delete(feedEncontrado.get());
        
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Feed> update(@PathVariable Long id, @RequestBody Feed feed){
        log.info("alterando usuario com id " + id);
        var feedEncontrado = repository.findById(id);

        if (feedEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        feed.setId(id);
        repository.save(feed);
        
        return ResponseEntity.ok(feed);

    }
}