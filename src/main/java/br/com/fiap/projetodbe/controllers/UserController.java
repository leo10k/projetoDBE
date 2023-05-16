package br.com.fiap.projetodbe.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
import br.com.fiap.projetodbe.models.Credencial;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.UserRepository;
import br.com.fiap.projetodbe.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "auth")
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
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String name, @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        Page<User> users = (name == null)?       
            useRepository.findAll(pageable): 
            useRepository.findByNameContaining(name, pageable);

        return assembler.toModel(users.map(User::toEntityModel));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registrer(@RequestBody @Valid User user){
        user.setPassword(encoder.encode(user.getPassword()));
        useRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<User> destroy(@PathVariable Long id){
        log.info("apagando usuario com id " + id);
        useRepository.delete(getUser(id));
        return ResponseEntity.noContent().build();
    }

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