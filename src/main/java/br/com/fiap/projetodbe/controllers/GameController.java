package br.com.fiap.projetodbe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.models.Game;

@RestController
public class GameController {
    
    @GetMapping("api/game")
    public Game show(){
        return new Game(1, "CS:GO", "FPS");
    }

}
