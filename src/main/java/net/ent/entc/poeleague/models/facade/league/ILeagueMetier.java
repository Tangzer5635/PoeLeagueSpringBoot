package net.ent.entc.poeleague.models.facade.league;

import net.ent.entc.poeleague.models.entities.League;
import java.util.Map;
import java.util.Set;

public interface ILeagueMetier {
    void save(League league);
    void delete(League league);
    Set<League> findAll();
    Map<League, Integer> findRewardPointsByLeague();
}