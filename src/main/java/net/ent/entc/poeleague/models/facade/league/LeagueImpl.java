package net.ent.entc.poeleague.models.facade.league;

import jakarta.transaction.Transactional;
import net.ent.entc.poeleague.models.entities.League;
import net.ent.entc.poeleague.models.repositories.LeagueRepository;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class LeagueImpl implements ILeagueMetier {

    private final LeagueRepository leagueRepository;

    public LeagueImpl(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public void save(League league) {
        leagueRepository.save(league);
    }

    @Override
    public void delete(League league) {
        leagueRepository.delete(league);
    }

    @Override
    public Set<League> findAll() {
        return Set.copyOf(leagueRepository.findAll());
    }

    @Override
    @Transactional
    public Map<League, Integer> findRewardPointsByLeague() {
        Map<League, Integer> result = new HashMap<>();
        List<League> leagues = leagueRepository.findAllWithChallenges();

        for (League league : leagues) {
            int total = league.getTotalRewardPoints();
            result.put(league, total);
        }
        return result;
    }
}