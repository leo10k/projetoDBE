package br.com.fiap.projetodbe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.models.User;

@RestController
public class UserController {
    
    @GetMapping("api/user")
    public User show(){
        return new User(1, "Leonardo", "Leonardo@gmail.com", "Masculino", "940028922", "12345");
    }

}
