package br.com.fiap.projetodbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String username);

}
