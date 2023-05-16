package br.com.fiap.projetodbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projetodbe.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String username);

    Page<User> findByNameContaining(String name, Pageable pageable);

}
