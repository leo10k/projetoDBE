package br.com.fiap.projetodbe.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.exception.RestNotFoundException;
import br.com.fiap.projetodbe.models.Credencial;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.UserRepository;
import br.com.fiap.projetodbe.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository repository;

    @Autowired
    AuthenticationManager manager;
    
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<User> registrer(@RequestBody @Valid User user){
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public List<User> index(){
        return repository.findAll();        
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<User> destroy(@PathVariable Long id){
        log.info("apagando usuario com id " + id);
        repository.delete(getUser(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid User user){
        log.info("alterando usuario com id " + id);
        getUser(id);
        user.setId(id);
        repository.save(user);
        return ResponseEntity.ok(user);
    }

    private User getUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestNotFoundException("user nao encontrado"));
    }
}