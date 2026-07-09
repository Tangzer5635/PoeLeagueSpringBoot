package net.ent.entc.poeleague.models.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.entc.poeleague.models.entities.communs.ValidUtils;
import net.ent.entc.poeleague.models.entities.communs.exceptions.ValidException;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntitiesFactory {

    /**
     * Fabrique un challenge grâce à ses rewardPoints, sa description et son nom
     * @param rewardPoints
     * @param description
     * @param nom
     * @return un challenge qui a été validé par bean validation
     * @throws ValidException
     */
    public static Challenge fabriquerChallenge(Integer rewardPoints, String description, String nom) throws ValidException {
        Challenge c = new Challenge();
        c.setRewardPoints(rewardPoints);
        c.setDescription(description);
        c.setNom(nom);
        ValidUtils.validate(c);
        return c;
    }

    /**
     * Fabrique un personnage grâce à son level, son build et son pseudo
     * @param level
     * @param build
     * @param pseudo
     * @return un personnage qui a été validé par bean validation
     * @throws ValidException
     */
    public static Personnage fabriquerPersonnage(Integer level, LabySpecialite build, String pseudo) throws ValidException {
        Personnage p = new Personnage();
        p.setLevel(level);
        p.setBuild(build);
        p.setPseudo(pseudo);
        ValidUtils.validate(p);
        return p;
    }

    /**
     * Fabrique une league grâce à son nom, sa date de début et sa date de fin
     * @param nom
     * @param dateDebut
     * @param dateFin
     * @return une league qui a été validé par bean validation
     * @throws ValidException
     */
    public static League fabriquerLeague(String nom, LocalDate dateDebut, LocalDate dateFin) throws ValidException {
        League l = new League();
        l.setNom(nom);
        l.setDateDebut(dateDebut);
        l.setDateFin(dateFin);
        ValidUtils.validate(l);
        return l;
    }
}
