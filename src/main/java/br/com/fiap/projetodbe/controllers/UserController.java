package br.com.fiap.projetodbe.controllers;

import br.com.fiap.projetodbe.exception.RestNotFoundException;
import br.com.fiap.projetodbe.models.Credencial;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.UserRepository;
import br.com.fiap.projetodbe.service.TokenService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository useRepository;

    @Autowired
    AuthenticationManager manager;
    
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @Operation(
        summary = "User get all",
        description = "Get all the data of the User's list")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String name, @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        Page<User> users = (name == null)?       
            useRepository.findAll(pageable): 
            useRepository.findByNameContaining(name, pageable);

        return assembler.toModel(users.map(User::toEntityModel));
    }

    @PostMapping("/register")
    @Operation(
        tags = "auth",
        summary = "User Register",
        description = "Register a data of a User"
    )
    public ResponseEntity<User> registrer(@RequestBody @Valid User user){
        user.setPassword(encoder.encode(user.getPassword()));
        useRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @Operation(
        tags = "auth",
        summary = "User Login",
        description = "Login a data of a User"
    )
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var user = useRepository.findByEmail(credencial.email()).orElseThrow();
        var token = tokenService.generateToken(credencial, user.getId());
        return ResponseEntity.ok(token);
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        log.info("buscando usuario com id: " + id );
        return ResponseEntity.ok(getUser(id));
    }

    @Operation(
        summary = "User Delete by id",
        description = "Delete by id the User's list")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<User> destroy(@PathVariable Long id){
        log.info("apagando usuario com id " + id);
        useRepository.delete(getUser(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "User Put by id",
        description = " Update the User id")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid fields"),
    })
    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid User user){
        log.info("alterando usuario com id " + id);
        getUser(id);
        user.setId(id);
        useRepository.save(user);
        return ResponseEntity.ok(user);
    }

    private User getUser(Long id) {
        return useRepository.findById(id).orElseThrow(() -> new RestNotFoundException("user nao encontrado"));
    }
}