package br.com.fiap.projetodbe.service;

import br.com.fiap.projetodbe.models.Credencial;
import br.com.fiap.projetodbe.models.Token;
import br.com.fiap.projetodbe.models.User;
import br.com.fiap.projetodbe.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Autowired
    UserRepository userRepository;

    @Value("${jwt.secret}")
    String secret;

    public Token generateToken( Credencial credencial, Long userId) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var token = JWT.create()
                    .withSubject(credencial.email())
                    .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                    .withIssuer("projetoDBE")
                    .sign(alg);

        return new Token(token, "JWT", "Bearer", userId);
    }

    public User valideAndGetUserBy(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var email =  JWT.require(alg)
            .withIssuer("projetoDBE")
            .build()
            .verify(token)
            .getSubject()
            ;

        return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found!"));
    }
    
}
