package net.ent.entc.poeleague.models.facade;

import net.ent.entc.poeleague.models.entities.Challenge;
import net.ent.entc.poeleague.models.entities.League;
import net.ent.entc.poeleague.models.entities.Personnage;
import net.ent.entc.poeleague.models.entities.referencies.LabySpecialite;
import net.ent.entc.poeleague.models.facade.exception.FacadeMetierException;

import java.util.Map;
import java.util.Set;

public interface IFacade {

    /**
     * Permet de sauvegarder un personnage
     *
     * @param personnage le personnage à sauvegarder
     */
    void savePersonnage(Personnage personnage);

    /**
     * Permet de sauvegarder un challenge
     *
     * @param challenge le challenge à sauvegarder
     */
    void saveChallenge(Challenge challenge);

    /**
     * Permet de sauvegarder une League
     *
     * @param league la league à sauvegarder
     */
    void saveLeague(League league);

    /**
     * Permet de supprimer un personnage
     *
     * @param personnage le personnage à supprimer
     */
    void deletePersonnage(Personnage personnage);

    /**
     * Permet de supprimer un challenge
     *
     * @param challenge le challenge à supprimer
     */
    void deleteChallenge(Challenge challenge);

    /**
     * Permet de supprimer une League
     *
     * @param league la league à supprimer
     */
    void deleteLeague(League league);

    /**
     * Permet de récupérer tous les personnages
     *
     * @return les personnages de la base
     */
    Set<Personnage> findAllPersonnages();

    /**
     * Permet de récupérer tous les challenges
     *
     * @return les challenges de la base
     */
    Set<Challenge> findAllChallenges();

    /**
     * Permet de récupérer toutes les leagues
     *
     * @return les leagues de la base
     */
    Set<League> findAllLeagues();

    /**
     * Permet de récupérer le challenge qui rapport le plus de points
     *
     * @return le challenge qui possède le plus de points
     */
    Challenge findBestChallenge();

    /**
     * Permet de récupérer les rewards points par League
     *
     * @return une Map contenant la league et la somme de ses reward points.
     */
    Map<League, Integer> findRewardPointsByLeague();

    /**
     * Permet de récupérer les 3 meilleurs builds de la league
     *
     * @param league league choisie
     * @return un Set des trois meilleurs build
     */
    Set<LabySpecialite> findTopThreeBestBuild(League league);

    /**
     * Permet de connaître les 3 meilleurs builds pour chaque League
     *
     * @return une Map contenant la league ainsi que les 3 meilleurs builds.
     */
    Map<League, Set<LabySpecialite>> findBestBuildByLeague();

    void initialisation() throws FacadeMetierException;

}
