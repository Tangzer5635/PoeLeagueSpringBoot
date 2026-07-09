package net.ent.entc.poeleague.models.repositories;

import net.ent.entc.poeleague.models.entities.Personnage;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {

    /**
     * Récupère les 3 meilleurs builds pour une League donnée
     * @param leagueId
     * @return les 3 best
     */
    @Query(value = "SELECT p.build " +
            "FROM league_personnage lp " +
            "JOIN personnage p ON p.id = lp.id_personnage " +
            "WHERE lp.id_league = :leagueId " +
            "GROUP BY p.build " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 3", nativeQuery = true)
    Set<LabySpecialite> findTopThreeBestBuild(@Param("leagueId") Long leagueId);
}