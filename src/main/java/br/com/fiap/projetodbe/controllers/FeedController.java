package br.com.fiap.projetodbe.controllers;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projetodbe.models.Feed;

@RestController
public class FeedController {

    @GetMapping("/api/feed")
    public Feed show() {
        return new Feed( "5k", "primeira semane de jogo e fiz um ace", "video.mp4");
    }

}