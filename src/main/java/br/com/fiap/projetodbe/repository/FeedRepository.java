package br.com.fiap.projetodbe.repository;

import br.com.fiap.projetodbe.models.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Page<Feed> findByGameContaining(String game, Pageable pageable);

    List<Feed> findAllByOrderByDataDesc();

    //List<Feed> findByDate(LocalDate data);

}
