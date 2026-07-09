package net.ent.entc.poeleague.models.repositories;

import net.ent.entc.poeleague.models.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    /**
     * Récupère toutes les leagues
     * @return toutes les leagues
     */
    @Query("SELECT DISTINCT l FROM League l LEFT JOIN FETCH l.lesChallenges")
    List<League> findAllWithChallenges();
}