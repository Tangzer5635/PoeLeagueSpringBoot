package net.ent.entc.poeleague.models.facade.challenge;

import net.ent.entc.poeleague.models.entities.Challenge;
import net.ent.entc.poeleague.models.entities.League;
import java.util.Set;

public interface IChallengeMetier {
    void save(Challenge challenge);
    void save(Challenge challenge, League league);
    void delete(Challenge challenge);
    Set<Challenge> findAll();
    Challenge findBest();
}