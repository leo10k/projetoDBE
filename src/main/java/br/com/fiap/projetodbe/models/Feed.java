package br.com.fiap.projetodbe.models;

import br.com.fiap.projetodbe.controllers.FeedController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    LocalDate data;

    @NotBlank @NotNull
    String titulo;

    @NotBlank @Size(min = 5, max = 255)
    String descricao;
    String img;

    @ManyToOne(optional = true)
    private Game game;

    @ManyToOne
    private User user;

    public EntityModel<Feed> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(FeedController.class).show(id)).withSelfRel(),
            linkTo(methodOn(FeedController.class).destroy(id)).withRel("delete")
            //linkTo(methodOn(FeedController.class).index(null, Pageable.unpaged())).withRel("all")
            //linkTo(methodOn(UserController.class).show(this.getUser().getId())).withRel("user")
            );
    }

}
