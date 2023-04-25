package br.com.fiap.projetodbe.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.models.Credencial;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.UserRepository;
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

    @PostMapping("/register")
    public ResponseEntity<User> registrar(@RequestBody @Valid User user){
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        return ResponseEntity.ok().build();
    }

}