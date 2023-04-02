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
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository repository;
    
    @GetMapping
    public List<User> index(){
        return repository.findAll();        
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid User user){
        log.info("cadastrando o usuario: " + user);
        
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id){
        log.info("buscando usuario com id: " + id );
        var user = getUser(id);

        

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> destroy(@PathVariable Long id){
        log.info("apagando usuario com id " + id);
        var user = getUser(id);

        repository.delete(user);
        
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