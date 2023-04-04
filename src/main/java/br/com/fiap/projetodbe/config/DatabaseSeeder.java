package br.com.fiap.projetodbe.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.projetodbe.models.Game;
import br.com.fiap.projetodbe.models.Genero;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.GameRepository;
import br.com.fiap.projetodbe.repository.UserRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        gameRepository.saveAll(List.of(
            new Game(1L, "Counter-Strike:Global-Ofenssive", "fps"),
            new Game(2L, "Sekiro", "Souls like"),
            new Game(3L, "Elden Ring", "Souls like"),
            new Game(4L, "God War", "Action / Adventure")
        ));

        userRepository.saveAll(List.of(
            new User(1L, "Pedro", "pedro@gmail.com", Genero.MASCULINO, "11912345678", "pedro12345"),
            new User(2L, "Joao", "joao@gmail.com", Genero.MASCULINO, "11127481274", "joao12345"),
            new User(3L, "Rafaela", "rafaela@gmail.com", Genero.FEMININO, "117531723", "rafaela12345"),
            new User(4L, "Monique", "monique@gmail.com", Genero.FEMININO, "11189492873", "monique12345"),
            new User(5L, "Leonardo", "leonardo@gmail.com", Genero.MASCULINO, "1193287433", "leonardo12345")

        ));
    }
    
}
