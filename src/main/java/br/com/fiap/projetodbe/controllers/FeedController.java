package br.com.fiap.projetodbe.controllers;

import br.com.fiap.projetodbe.exception.RestNotFoundException;
import br.com.fiap.projetodbe.models.Feed;
import br.com.fiap.projetodbe.repository.FeedRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeds")
@SecurityRequirement(name = "bearer-key")
public class FeedController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    FeedRepository feedRepository; //IoD

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @Operation(
        summary = "Feed get all",
        description = "Get all the data of the Feed's list")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @GetMapping
    public List<Feed> getAll() {
        return feedRepository.findAllByOrderByDataDesc();
    }


//    @GetMapping
//    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String game, @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
//        Page<Feed> feeds = (game == null)?
//            feedRepository.findAll(pageable):
//            feedRepository.findByGameContaining(game, pageable);
//
//        return assembler.toModel(feeds.map(Feed::toEntityModel)); //reference metohd
//    }


    @Operation(
        summary = "Feed Post",
        description = "Create a new Feed")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "feed cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "erro na validação dos dados da requisição")})
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Feed feed) {
        log.info("cadastrando o feed: " + feed);
        feedRepository.save(feed);
        return ResponseEntity
        .created(feed.toEntityModel().getRequiredLink("self").toUri())
        .body(feed.toEntityModel());
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Detalhes do feed",
        description = "Retorna os dados de um feed com id especificado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    public EntityModel<Feed> show(@PathVariable Long id) {
        log.info("buscando feed com id: " + id );
        return getFeed(id).toEntityModel();
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Feed Delete by id",
        description = "Delete by the Feed's id")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    public ResponseEntity<Feed> destroy(@PathVariable Long id) {
        log.info("apagando feed com id " + id);
        feedRepository.delete(getFeed(id));  
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(
        summary = "Feed Put by id",
        description = " Update the Feed id")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
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