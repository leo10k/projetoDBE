package br.com.fiap.projetodbe.models;

import br.com.fiap.projetodbe.controllers.GameController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feed> feeds;

    public EntityModel<Game> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(GameController.class).show(id)).withSelfRel(),
            linkTo(methodOn(GameController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(GameController.class).index(null, Pageable.unpaged())).withRel("all")
            );
    }
    
}
