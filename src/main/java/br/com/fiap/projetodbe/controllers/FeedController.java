package br.com.fiap.projetodbe.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String game, @PageableDefault(size = 5) Pageable pageable) {
        Page<Feed> feeds = (game == null)? 
            feedRepository.findAll(pageable):
            feedRepository.findByGameContaining(game, pageable);

        return assembler.toModel(feeds.map(Feed::toEntityModel)); //reference metohd
    }
    

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Feed feed) {
        log.info("cadastrando o feed: " + feed);
        feedRepository.save(feed);
        return ResponseEntity
        .created(feed.toEntityModel().getRequiredLink("self").toUri())
        .body(feed.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Feed> show(@PathVariable Long id) {
        log.info("buscando feed com id: " + id );
        return getFeed(id).toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Feed> destroy(@PathVariable Long id) {
        log.info("apagando feed com id " + id);
        feedRepository.delete(getFeed(id));  
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public EntityModel<Feed> update(@PathVariable Long id, @RequestBody @Valid Feed feed){
        log.info("alterando feed com id " + id);
        getFeed(id);
        feed.setId(id);
        feedRepository.save(feed);
        return feed.toEntityModel();
    }

    private Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(() -> new RestNotFoundException("feed nao encontrado"));
    }
}