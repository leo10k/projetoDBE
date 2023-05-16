package br.com.fiap.projetodbe.models;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.projetodbe.controllers.GameController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotBlank
    private String nome;
    
    @NotNull @NotBlank
    private String genero;

    public EntityModel<Game> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(GameController.class).show(id)).withSelfRel(),
            linkTo(methodOn(GameController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(GameController.class).index(null, Pageable.unpaged())).withRel("all")
            );
    }
    
}
