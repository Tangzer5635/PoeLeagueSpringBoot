package net.ent.entc.poeleague.models.facade.personnage;

import net.ent.entc.poeleague.models.entities.League;
import net.ent.entc.poeleague.models.entities.Personnage;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;
import java.util.Map;
import java.util.Set;

public interface IPersonnageMetier {
    void save(Personnage personnage);
    void save(Personnage personnage, League league, Integer position);
    void delete(Personnage personnage);
    Set<Personnage> findAll();
    Set<LabySpecialite> findTopThreeBestBuild(League league);
    Map<League, Set<LabySpecialite>> findBestBuildByLeague();
}