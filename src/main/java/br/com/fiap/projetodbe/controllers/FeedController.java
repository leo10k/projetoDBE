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

import br.com.fiap.projetodbe.exception.RestNotFoundException;
import br.com.fiap.projetodbe.models.Feed;
import br.com.fiap.projetodbe.repository.FeedRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    FeedRepository repository; //IoD

    @GetMapping
    public List<Feed> index() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Feed feed) {
        // if(result.hasErrors()) return ResponseEntity.badRequest().body(new
        // RestValidationError("erro de validação"));
        log.info("cadastrando o feed: " + feed);
        
        repository.save(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body(feed);
    }

    @GetMapping("{id}")
    public ResponseEntity<Feed> show(@PathVariable Long id) {
        log.info("buscando feed com id: " + id );
        var feed = getFeed(id);

        return ResponseEntity.ok(feed);
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Feed> destroy(@PathVariable Long id) {
        log.info("apagando feed com id " + id);

        var feed = getFeed(id);

        repository.delete(feed);
        
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Feed> update(@PathVariable Long id, @RequestBody @Valid Feed feed){
        log.info("alterando feed com id " + id);
        getFeed(id);

        feed.setId(id);
        repository.save(feed);
        
        return ResponseEntity.ok(feed);

    }

    private Feed getFeed(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestNotFoundException("feed nao encontrado"));
    }

}