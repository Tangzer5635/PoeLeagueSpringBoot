package net.ent.entc.poeleague.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;

@Entity
@Table(name = "personnage", uniqueConstraints = @UniqueConstraint(name = "uk___personnage___nom", columnNames = {"pseudo"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = {"pseudo"})
@ToString(callSuper = true, of = {"level", "build", "pseudo"})
public class Personnage extends AbstractEntity {

    /**
     * Niveau du personnage entre 1 et 100
     */
    @Getter
    @Setter
    @NotNull(message = "Le level ne doit pas être vide")
    @Min(value = 1, message = "Le level doit être au minimum 1")
    @Max(value = 100, message = "Le level ne doit pas dépasser 100")
    @Column(name = "level", nullable = false)
    private Integer level;

    /**
     * Spécialité du personnage venant de l'enum LabySpecialite
     */
    @Getter
    @Setter
    @NotNull(message = "Le build ne peut pas être vide")
    @Enumerated(EnumType.STRING)
    @Column(name = "build", nullable = false, length = 50)
    private LabySpecialite build;

    /**
     * Pseudo du personnage entre 1 et 100 caractères
     */
    @Getter
    @Setter
    @NotBlank(message = "Le pseudo ne doit pas être vide")
    @Size(min = 1, max = 100, message = "Le pseudo doit avoir entre 1 et 100 caractères")
    @Column(name = "pseudo", nullable = false, length = 100, unique = true)
    private String pseudo;
}