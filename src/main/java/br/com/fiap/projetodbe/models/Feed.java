package br.com.fiap.projetodbe.models;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.projetodbe.controllers.FeedController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


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

    @OneToOne
    private Game game;

    @ManyToOne
    private User user;

    public EntityModel<Feed> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(FeedController.class).show(id)).withSelfRel(),
            linkTo(methodOn(FeedController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(FeedController.class).index(null, Pageable.unpaged())).withRel("all"),
            linkTo(methodOn(FeedController.class).show(this.getUser().getId())).withRel("user")
            );
    }

}
