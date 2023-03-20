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

import br.com.fiap.projetodbe.models.Feed;

@RestController
public class FeedController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    List<Feed> feeds = new ArrayList<>();
    
    @GetMapping("api/feeds")
    public List<Feed> index() {
        return feeds;        
    }

    @PostMapping("/api/feeds")
    public ResponseEntity<Feed> create(@RequestBody Feed feed) {
        log.info("cadastrando o usuario: " + feed);
        feed.setId(feeds.size() + 1l);
        feeds.add(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body(feed);
    }

    @GetMapping("/api/feeds/{id}")
    public ResponseEntity<Feed> show(@PathVariable Long id) {
        log.info("buscando usuario com id: " + id );
        var feedEncontrado = feeds.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (feedEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(feedEncontrado.get());
    }

    @DeleteMapping("/api/feeds/{id}")
    public ResponseEntity<Feed> destroy(@PathVariable Long id) {
        log.info("apagando usuario com id " + id);
        var feedEncontrado = feeds.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (feedEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        feeds.remove(feedEncontrado.get());
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/api/feeds/{id}")
    public ResponseEntity<Feed> update(@PathVariable Long id, @RequestBody Feed feed){
        log.info("alterando usuario com id " + id);
        var feedEncontrado = feeds.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (feedEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        feeds.remove(feedEncontrado.get());
        feed.setId(id);
        feeds.add(feed);
        
        return ResponseEntity.ok(feed);

    }

}