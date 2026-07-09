package net.ent.entc.poeleague.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "league", uniqueConstraints = @UniqueConstraint(name = "uk___league_nom", columnNames = {"nom"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(callSuper = true, of = {"nom", "dateFin", "dateDebut"})
public class League extends AbstractEntity {

    /**
     * Nom de la league entre 5 et 200 caractères
     */
    @Getter
    @Setter
    @NotBlank(message = "Le nom ne doit pas être vide")
    @Size(min = 5, max = 200, message = "Le nom doit avoir entre 5 et 200 caractères")
    @Column(name = "nom", nullable = false, length = 200, unique = true)
    private String nom;

    /**
     * Date du début de la league
     */
    @Getter
    @Setter
    @NotNull(message = "La date de début ne doit pas être vide")
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    /**
     * Date de fin de la league
     */
    @Getter
    @Setter
    @Column(name = "date_fin", nullable = true)
    private LocalDate dateFin;

    /**
     * Set de challenges
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_league", foreignKey = @ForeignKey(name = "fk___challenge___league"))
    private Set<Challenge> lesChallenges =  new HashSet<>();

    /**
     * Récupère l'ensemble des challenges via le set
     * @return les challenges
     */
    public Set<Challenge> getLesChallenges() {
        Set<Challenge> tries = new TreeSet<>(
                Comparator.comparing(Challenge::getRewardPoints).reversed()
        );
        tries.addAll(lesChallenges);
        return Collections.unmodifiableSet(tries);
    }

    /**
     * Map de personnage avec d'un côté les personnage qui sont lié à une quantité
     */
    @ElementCollection
    @CollectionTable(name = "league_personnage", joinColumns = @JoinColumn(name = "id_league"), foreignKey = @ForeignKey(name = "fk___league_personnage___id_league"))
    @MapKeyJoinColumn(name = "id_personnage", foreignKey = @ForeignKey(name = "fk___league_personnage___personnage"))
    @Column(name = "quantite", nullable = false)
    private Map<Personnage, Integer> classement = new HashMap<>();

    /**
     * Récupère les classements
     * @return les classements
     */
    public Map<Personnage, Integer> getClassement() {
        return Collections.unmodifiableMap(classement);
    }

    /**
     * Ajoute un challenge demandé dans le set
     * @param c
     */
    public void ajouterChallenge(Challenge c) {
        lesChallenges.add(c);
    }

    /**
     * ajouet un personnage et sa quantité dans la map
     * @param p
     * @param i
     */
    public void ajouterPersonnage(Personnage p, Integer i) {
        classement.put(p, i);
    }

    /**
     * Récupère l'ensemble des points de tous les challenges
     * @return les points de tous les challenges
     */
    public int getTotalRewardPoints() {
        return lesChallenges.stream()
                .mapToInt(Challenge::getRewardPoints)
                .sum();
    }

    /**
     * Vérifie que les challenges respectent bien les règles métier
     * @return si oui ou non respecte la règle
     */
    public boolean contientMinRewardPoints() {
        return getTotalRewardPoints() >= 50;
    }

    /**
     * Vérifie qu'ils possèdent au moins 10 Challenges et que la somme totale des points des Challenges est supérieure ou égale à 50.
     * @return si oui ou non respecte la règle
     */
    public boolean estValide() {
        return lesChallenges.size() >= 10 && contientMinRewardPoints();
    }
}