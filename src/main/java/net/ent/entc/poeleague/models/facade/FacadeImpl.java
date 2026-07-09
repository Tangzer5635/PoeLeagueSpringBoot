// FacadeImpl.java
package net.ent.entc.poeleague.models.facade;

import jakarta.transaction.Transactional;
import net.ent.entc.poeleague.models.entities.Challenge;
import net.ent.entc.poeleague.models.entities.EntitiesFactory;
import net.ent.entc.poeleague.models.entities.League;
import net.ent.entc.poeleague.models.entities.Personnage;
import net.ent.entc.poeleague.models.entities.communs.exceptions.ValidException;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;
import net.ent.entc.poeleague.models.facade.challenge.IChallengeMetier;
import net.ent.entc.poeleague.models.facade.exception.FacadeMetierException;
import net.ent.entc.poeleague.models.facade.league.ILeagueMetier;
import net.ent.entc.poeleague.models.facade.personnage.IPersonnageMetier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class FacadeImpl implements IFacade {

    private final IChallengeMetier challengeMetier;
    private final ILeagueMetier leagueMetier;
    private final IPersonnageMetier personnageMetier;

    public FacadeImpl(IChallengeMetier challengeMetier, ILeagueMetier leagueMetier, IPersonnageMetier personnageMetier) {
        this.challengeMetier = challengeMetier;
        this.leagueMetier = leagueMetier;
        this.personnageMetier = personnageMetier;
    }

    /**
     * sauvegarde le personnage dans le repository
     * @param personnage le personnage à sauvegarder
     */
    @Override
    public void savePersonnage(Personnage personnage) {
        personnageMetier.save(personnage);
    }

    /**
     * sauvegarde le challenge dans le repository
     * @param challenge le challenge à sauvegarder
     */
    @Override
    public void saveChallenge(Challenge challenge) {
        challengeMetier.save(challenge);
    }

    /**
     * sauvegarde le league dans le repository
     * @param league la league à sauvegarder
     */
    @Override
    public void saveLeague(League league) {
        leagueMetier.save(league);
    }

    /**
     * supprime le personnage dans le repository
     * @param personnage le personnage à supprimer
     */
    @Override
    public void deletePersonnage(Personnage personnage) {
        personnageMetier.delete(personnage);
    }

    /**
     * supprime le challenge dans le repository
     * @param challenge le challenge à supprimer
     */
    @Override
    public void deleteChallenge(Challenge challenge) {
        challengeMetier.delete(challenge);
    }

    /**
     * supprime le league dans le repository
     * @param league la league à supprimer
     */
    @Override
    public void deleteLeague(League league) {
        leagueMetier.delete(league);
    }

    /**
     * Récupère tous les personnages
     * @return les personnages
     */
    @Override
    public Set<Personnage> findAllPersonnages() {
        return personnageMetier.findAll();
    }

    /**
     * Récupère tous les challenges
     * @return les challenges
     */
    @Override
    public Set<Challenge> findAllChallenges() {
        return challengeMetier.findAll();
    }

    /**
     * Récupère toutes les leagues
     * @return les leagues
     */
    @Override
    public Set<League> findAllLeagues() {
        return leagueMetier.findAll();
    }

    /**
     * Trouve le meilleure challenge grâce a son nombre de points
     * @return le challenge
     */

    @Override
    public Challenge findBestChallenge() {
        return challengeMetier.findBest();
    }

    /**
     * Affiche les reward Points pour chaques leagues
     * @return les reward Points
     */
    @Override
    public Map<League, Integer> findRewardPointsByLeague() {
        return leagueMetier.findRewardPointsByLeague();
    }

    /**
     * Renvoie la liste des 3 meilleures builds pour une league
     * @param league league choisie
     * @return la liste des builds
     */
    @Override
    public Set<LabySpecialite> findTopThreeBestBuild(League league) {
        return personnageMetier.findTopThreeBestBuild(league);
    }

    /**
     * Renvoie la liste des 3 meilleures builds pour chaque league
     * @return les 3 meilleures builds par league
     */
    @Override
    public Map<League, Set<LabySpecialite>> findBestBuildByLeague() {
        return personnageMetier.findBestBuildByLeague();
    }

    /**
     * Initialise les tables et gère les exception de l'import du csv
     * @throws FacadeMetierException
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void initialisation() throws FacadeMetierException {
        try {
            initPoeLeague();
        } catch (IOException | URISyntaxException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        } catch (ValidException e) {
            throw new FacadeMetierException(e.affichageConstraintViolation());
        }
    }

    /**
     * Charge le fichier et lit l'ensemble de ses lignes
     * @param path
     * @return l'ensemble des lignes
     * @throws IOException
     * @throws URISyntaxException
     */
    private List<String> chargementFichier(String path) throws IOException, URISyntaxException {
        Path pathHabitant = Path.of(Objects.requireNonNull(FacadeImpl.class.getResource(path)).toURI());
        List<String> lignes = Files.readAllLines(pathHabitant);
        return lignes;
    }

    /**
     * Découpe le fichier et effectue un traitement différent si la ligne concerne
     * une league, un challenge ou un personnage.
     * @throws IOException
     * @throws URISyntaxException
     * @throws ValidException
     */
    private void initPoeLeague() throws IOException, URISyntaxException, ValidException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> lignes = chargementFichier("/csv/InitPoeLeague.csv");

        League leagueEnCours = null;

        for (String ligne : lignes) {
            String[] containsLine = ligne.split(";", -1);

            switch (containsLine[0]) {
                case "LEAGUE" -> {
                    LocalDate dateFin = containsLine[3].isEmpty()
                            ? null
                            : LocalDate.parse(containsLine[3], formatter);

                    leagueEnCours = EntitiesFactory.fabriquerLeague(
                            containsLine[1],
                            LocalDate.parse(containsLine[2], formatter),
                            dateFin
                    );
                    leagueMetier.save(leagueEnCours);
                }
                case "CHALLENGE" -> {
                    Challenge cha = EntitiesFactory.fabriquerChallenge(
                            Integer.valueOf(containsLine[3]),
                            containsLine[2],
                            containsLine[1]
                    );
                    challengeMetier.save(cha, leagueEnCours);
                }
                case "PERSONNAGE" -> {
                    Personnage per = EntitiesFactory.fabriquerPersonnage(
                            Integer.valueOf(containsLine[4]),
                            LabySpecialite.valueOf(containsLine[3].toUpperCase()),
                            containsLine[2]
                    );
                    Integer position = Integer.valueOf(containsLine[1]);
                    personnageMetier.save(per, leagueEnCours, position);
                }
            }
        }
    }
}