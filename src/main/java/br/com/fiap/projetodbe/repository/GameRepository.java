package br.com.fiap.projetodbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findByNomeContaining(String nome, Pageable pageable);
    
}
