package net.ent.entc.poeleague.models.facade.personnage;

import net.ent.entc.poeleague.models.entities.League;
import net.ent.entc.poeleague.models.entities.Personnage;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;
import net.ent.entc.poeleague.models.repositories.PersonnageRepository;
import net.ent.entc.poeleague.models.repositories.LeagueRepository;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class PersonnageImpl implements IPersonnageMetier {

    private final PersonnageRepository personnageRepository;
    private final LeagueRepository leagueRepository;

    public PersonnageImpl(PersonnageRepository personnageRepository, LeagueRepository leagueRepository) {
        this.personnageRepository = personnageRepository;
        this.leagueRepository = leagueRepository;
    }

    @Override
    public void save(Personnage personnage) {
        save(personnage, null, null);
    }

    @Override
    public void save(Personnage personnage, League league, Integer position) {
        personnageRepository.save(personnage);
        if (league != null && position != null) {
            league.ajouterPersonnage(personnage, position);
            leagueRepository.save(league);
        }
    }

    @Override
    public void delete(Personnage personnage) {
        personnageRepository.delete(personnage);
    }

    @Override
    public Set<Personnage> findAll() {
        return Set.copyOf(personnageRepository.findAll());
    }

    @Override
    public Set<LabySpecialite> findTopThreeBestBuild(League league) {
        return personnageRepository.findTopThreeBestBuild(league.getId());
    }

    @Override
    public Map<League, Set<LabySpecialite>> findBestBuildByLeague() {
        Map<League, Set<LabySpecialite>> result = new HashMap<>();
        Set<League> leagues = Set.copyOf(leagueRepository.findAll());
        for (League league : leagues) {
            Set<LabySpecialite> builds = findTopThreeBestBuild(league);
            result.put(league, builds);
        }
        return result;
    }
}