package br.com.fiap.projetodbe.config;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.projetodbe.models.Feed;
import br.com.fiap.projetodbe.models.Game;
import br.com.fiap.projetodbe.models.Genero;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.FeedRepository;
import br.com.fiap.projetodbe.repository.GameRepository;
import br.com.fiap.projetodbe.repository.UserRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedRepository feedRepository;

    @Override
    public void run(String... args) throws Exception {
        Game g1 = new Game(1L, "Counter-Strike:Global-Ofenssive", "fps");
        Game g2 = new Game(2L, "Sekiro", "Souls like");
        Game g3 = new Game(3L, "Elden Ring", "Souls like");
        Game g4 = new Game(4L, "God War", "Action / Adventure");
        gameRepository.saveAll(List.of(g1,g2,g3,g4));

        User u1 = new User(1L, "Pedro", "pedro@gmail.com", Genero.MASCULINO, "11912345678", "pedro12345");
        User u2 = new User(2L, "Joao", "joao@gmail.com", Genero.MASCULINO, "11127481274", "joao12345");
        User u3 = new User(3L, "Rafaela", "rafaela@gmail.com", Genero.FEMININO, "117531723", "rafaela12");
        User u4 = new User(4L, "Monique", "monique@gmail.com", Genero.FEMININO, "11189492873", "monique12345");
        User u5 = new User(5L, "Leonardo", "leonardo@gmail.com", Genero.MASCULINO, "1193287433", "leonardo12345");
        userRepository.saveAll(List.of(u1,u2,u3,u4,u5));

        feedRepository.saveAll(List.of(
            Feed.builder().data(LocalDate.of(2023, 4, 10)).titulo("Meu priemiro Ace").descricao("Se liga nesse Clutch!").img("vid1.mp4").game(g1).user(u1).build(),
            Feed.builder().data(LocalDate.of(2023, 3, 29)).titulo("Matando a Malenia").descricao("Esse boss foi muito dificil!").img("vid2.mp4").game(g3).user(u3).build(),
            Feed.builder().data(LocalDate.of(2023, 4, 1)).titulo("Zerando Sekiro").descricao("demorei 70 horas para zerar").img("vid3.mp4").game(g2).user(u2).build(),
            Feed.builder().data(LocalDate.of(2023, 4, 6)).titulo("Joguei com fallen").descricao("Cai contra o time do fallen interio").img("img1.jpg").game(g1).user(u5).build(),
            Feed.builder().data(LocalDate.of(2023, 2, 23)).titulo("Olha esse grafico").descricao("Sem dulvidas, o jogo com melhor grafico que joguei").img("img2.jpg").game(g4).user(u1).build(),
            Feed.builder().data(LocalDate.of(2023, 1, 21)).titulo("Recebi o beta do cs").descricao("Finalmente recebi o beta").img("img3.jpg").game(g1).user(u1).build(),
            Feed.builder().data(LocalDate.of(2023, 3, 28)).titulo("Zerando Elden ring").descricao("zerei elden ring ").img("vid4.mp4").game(g3).user(u4).build(),
            Feed.builder().data(LocalDate.of(2023, 3, 9)).titulo("Olha esse dragao").descricao("Que dragao enorme que encontrei no mapa").img("img4.jpg").game(g3).user(u4).build(),
            Feed.builder().data(LocalDate.of(2023, 4, 3)).titulo("Jogando multiplayer").descricao("Consegui jogar com meu amigo").img("img5.jpg").game(g3).user(u5).build(),
            Feed.builder().data(LocalDate.of(2023, 3, 18)).titulo("So one tap").descricao("Que clipe maravilhoso que fiz").img("vid5.mp4").game(g1).user(u2).build(),
            Feed.builder().data(LocalDate.of(2023, 2, 10)).titulo("Que ratake lindo").descricao("Que time maravilhoso").img("vid6.mp4").game(g1).user(u3).build()

        ));

    }
    
}
