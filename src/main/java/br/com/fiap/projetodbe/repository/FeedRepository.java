package br.com.fiap.projetodbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Page<Feed> findByGameContaining(String game, Pageable pageble);
    
    //List<Feed> findByDate(LocalDate data);

}
