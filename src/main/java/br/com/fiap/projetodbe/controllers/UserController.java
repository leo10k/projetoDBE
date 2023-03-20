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

import br.com.fiap.projetodbe.models.User;

@RestController
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    List<User> users = new ArrayList<>();
    
    @GetMapping("api/users")
    public List<User> index(){
        return users;        
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> create(@RequestBody User user){
        log.info("cadastrando o usuario: " + user);
        user.setId(users.size() + 1l);
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> show(@PathVariable Long id){
        log.info("buscando usuario com id: " + id );
        var userEncontrado = users.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (userEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(userEncontrado.get());
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<User> destroy(@PathVariable Long id){
        log.info("apagando usuario com id " + id);
        var userEncontrado = users.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (userEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        users.remove(userEncontrado.get());
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        log.info("alterando usuario com id " + id);
        var userEncontrado = users.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (userEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        users.remove(userEncontrado.get());
        user.setId(id);
        users.add(user);
        
        return ResponseEntity.ok(user);

    }


}
