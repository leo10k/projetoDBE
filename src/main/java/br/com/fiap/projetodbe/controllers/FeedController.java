package br.com.fiap.projetodbe.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.exception.RestNotFoundException;
import br.com.fiap.projetodbe.models.Feed;
import br.com.fiap.projetodbe.repository.FeedRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    FeedRepository feedRepository; //IoD

    @GetMapping
    public Page<Feed> index(@RequestParam(required = false) String game, @PageableDefault(size = 5) Pageable pageble) {
        if (game == null) 
            return feedRepository.findAll(pageble);
        
        return feedRepository.findByGameContaining(game, pageble);
    }
    

    @PostMapping
    public ResponseEntity<Feed> create(@RequestBody @Valid Feed feed) {
        log.info("cadastrando o feed: " + feed);
        feedRepository.save(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body(feed);
    }

    @GetMapping("{id}")
    public ResponseEntity<Feed> show(@PathVariable Long id) {
        log.info("buscando feed com id: " + id );
        return ResponseEntity.ok(getFeed(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Feed> destroy(@PathVariable Long id) {
        log.info("apagando feed com id " + id);
        feedRepository.delete(getFeed(id));  
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Feed> update(@PathVariable Long id, @RequestBody @Valid Feed feed){
        log.info("alterando feed com id " + id);
        getFeed(id);
        feed.setId(id);
        feedRepository.save(feed);
        return ResponseEntity.ok(feed);
    }

    private Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(() -> new RestNotFoundException("feed nao encontrado"));
    }

}