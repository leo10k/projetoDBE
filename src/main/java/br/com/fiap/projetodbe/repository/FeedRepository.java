package br.com.fiap.projetodbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    
    //List<Feed> findByDate(LocalDate data);

}
