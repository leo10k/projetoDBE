package br.com.fiap.projetodbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
