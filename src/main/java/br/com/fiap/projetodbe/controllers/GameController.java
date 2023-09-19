package br.com.fiap.projetodbe.controllers;

import br.com.fiap.projetodbe.exception.RestNotFoundException;
import br.com.fiap.projetodbe.models.Game;
import br.com.fiap.projetodbe.repository.GameRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController {
    
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;
    

    @Operation(
        summary = "Game get all",
        description = "Get all the data of the Game's list")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String nome, @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        Page<Game> games = (nome == null)?       
            gameRepository.findAll(pageable): 
            gameRepository.findByNomeContaining(nome, pageable);

        return assembler.toModel(games.map(Game::toEntityModel));
    }


    @Operation(
        summary = "Game Post",
        description = "Create a new Game")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "feed cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "erro na validação dos dados da requisição")})
    @PostMapping
    public ResponseEntity<Game> create(@RequestBody @Valid Game game) {
        log.info("cadastrando o game: " + game);
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }


    @Operation(
        summary = "Detalhes do game",
        description = "Retorna os dados de um game com id especificado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @GetMapping("{id}")
    public ResponseEntity<Game> show(@PathVariable Long id) {
        log.info("buscando game com id: " + id );
        return ResponseEntity.ok(getGame(id));
    }



    @Operation(
        summary = "Game Delete by id",
        description = "Delete by the Game's id")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Game> destroy(@PathVariable Long id) {
        log.info("apagando game com id " + id);
        gameRepository.delete(getGame(id));
        return ResponseEntity.noContent().build();
    }


    @Operation(
        summary = "Game Put by id",
        description = " Update the Game id")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @PutMapping("{id}")
    public ResponseEntity<Game> update(@PathVariable Long id, @RequestBody @Valid Game game){
        log.info("alterando game com id " + id);
        getGame(id);
        game.setId(id);
        gameRepository.save(game);
        return ResponseEntity.ok(game);
    }

    private Game getGame(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new RestNotFoundException("game nao encontrado"));
    }

}