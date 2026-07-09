package net.ent.entc.poeleague.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "challenge", uniqueConstraints = @UniqueConstraint(name = "uk___challenge___nom", columnNames = {"nom"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(callSuper = true, of = {"rewardPoints", "description", "nom"})
public class Challenge extends AbstractEntity {

    /**
     * Point(s) de récompense d'un challenge
     */
    @Getter
    @Setter
    @NotNull(message = "Les rewardPoints ne doivent pas être vides")
    @Column(name = "reward_points", nullable = false)
    private Integer rewardPoints;

    /**
     * Description du challenge (non écrit comme dans le TP, car desc est bloqué par Postgres)
     * compris entre 1 et 255 caractères
     */
    @Getter
    @Setter
    @NotBlank(message = "La description ne doit pas être vide")
    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    /**
     * Nom du challenge compris entre 1 et 100 caractères
     */
    @Getter
    @Setter
    @NotBlank(message = "Le nom ne doit pas être vide")
    @Size(min = 1, max = 100, message = "Le nom doit être entre 1 et 100 caractères")
    @Column(name = "nom", nullable = false, length = 100, unique = true)
    private String nom;
}