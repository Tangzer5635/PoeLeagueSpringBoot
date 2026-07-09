package net.ent.entc.poeleague.models.facade.challenge;

import net.ent.entc.poeleague.models.entities.Challenge;
import net.ent.entc.poeleague.models.entities.League;
import net.ent.entc.poeleague.models.repositories.ChallengeRepository;
import net.ent.entc.poeleague.models.repositories.LeagueRepository;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class ChallengeImpl implements IChallengeMetier {

    private final ChallengeRepository challengeRepository;
    private final LeagueRepository leagueRepository;

    public ChallengeImpl(ChallengeRepository challengeRepository, LeagueRepository leagueRepository) {
        this.challengeRepository = challengeRepository;
        this.leagueRepository = leagueRepository;
    }

    @Override
    public void save(Challenge challenge) {
        save(challenge, null);
    }

    @Override
    public void save(Challenge challenge, League league) {
        challengeRepository.save(challenge);
        if (league != null) {
            league.ajouterChallenge(challenge);
            leagueRepository.save(league);
        }
    }

    @Override
    public void delete(Challenge challenge) {
        challengeRepository.delete(challenge);
    }

    @Override
    public Set<Challenge> findAll() {
        return Set.copyOf(challengeRepository.findAll());
    }

    @Override
    public Challenge findBest() {
        return challengeRepository.findBestChallenge();
    }
}