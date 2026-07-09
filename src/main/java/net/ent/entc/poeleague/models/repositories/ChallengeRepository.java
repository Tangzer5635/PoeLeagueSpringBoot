package net.ent.entc.poeleague.models.repositories;

import net.ent.entc.poeleague.models.entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    /**
     * Récupère le meilleur challenge
     * @return le challenge
     */
    @Query(value = "SELECT * FROM challenge ORDER BY reward_points DESC LIMIT 1", nativeQuery = true)
    Challenge findBestChallenge();
}