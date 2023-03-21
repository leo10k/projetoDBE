package br.com.fiap.projetodbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
    
}
