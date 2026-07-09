# 🎮 PoE League - Spring Boot

Application de gestion de leagues Path of Exile développée avec Spring Boot, JPA/Hibernate et PostgreSQL.

## 📋 Description

Cette application permet de gérer un système de leagues Path of Exile avec :
- **Leagues** : périodes de jeu avec date de début/fin
- **Challenges** : défis rapportant des points de récompense
- **Personnages** : joueurs avec un pseudo, un level et un build (spécialité)
- **Classement** : positionnement des personnages dans chaque league

## 🛠️ Stack technique

- **Java 21**
- **Spring Boot 4.1** (Web, Data JPA, Validation)
- **Hibernate 7.4** (ORM)
- **PostgreSQL** (Base de données)
- **Lombok** (Réduction du boilerplate)
- **Maven** (Build)

## 🏗️ Architecture

```text
src/main/java/net/ent/entc/poeleague/
├── PoeleagueApplication.java          # Point d'entrée Spring Boot
└── models/
    ├── entities/                       # Entités JPA
    │   ├── AbstractEntity.java
    │   ├── Challenge.java
    │   ├── League.java
    │   ├── Personnage.java
    │   ├── EntitiesFactory.java        # Factory pour créer les entités
    │   ├── communs/                    # Utilitaires de validation
    │   └── referencies/
    │       └── LabySpecialite.java     # Enum des builds
    │
    ├── repositories/                   # Couche d'accès aux données (Spring Data JPA)
    │   ├── ChallengeRepository.java
    │   ├── LeagueRepository.java
    │   └── PersonnageRepository.java
    │
    └── facade/                         # Couche métier
        ├── IFacade.java                # Interface principale (façade)
        ├── FacadeImpl.java             # Implémentation qui orchestre les métiers
        ├── challenge/
        │   ├── IChallengeMetier.java
        │   └── ChallengeImpl.java
        ├── league/
        │   ├── ILeagueMetier.java
        │   └── LeagueImpl.java
        ├── personnage/
        │   ├── IPersonnageMetier.java
        │   └── PersonnageImpl.java
        └── exception/
            └── FacadeMetierException.java
```

## 📐 Modèle de données

### Relations

- **League** → **Challenge** : `@OneToMany` (une league a plusieurs challenges, FK `id_league` dans la table `challenge`)
- **League** → **Personnage** : `@ElementCollection` sur `Map<Personnage, Integer>` (classement des personnages)
- **Personnage** → **LabySpecialite** : `@Enumerated` (build/spécialité du personnage)

### Contraintes métier

#### League
- Nom entre 5 et 200 caractères, unique et obligatoire
- Date de début obligatoire, date de fin optionnelle
- Une league est **valide** si elle possède au moins 10 challenges et que la somme totale des points ≥ 50

#### Challenge
- Nom entre 1 et 100 caractères, unique et obligatoire
- Description max 255 caractères
- Reward points obligatoires

#### Personnage
- Pseudo entre 1 et 100 caractères, unique et obligatoire
- Level entre 1 et 100, obligatoire
- Build obligatoire (parmi les 19 spécialités PoE)

## 🚀 Installation

### Prérequis

- Java 21+
- Maven 3.8+
- PostgreSQL 14+

### Configuration de la base de données

Créer une base PostgreSQL puis configurer `application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/poeleague
spring.datasource.username=votre_user
spring.datasource.password=votre_password
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Lancement

```bash
# Cloner le dépôt
git clone https://github.com/votre-user/PoeLeagueSpringBoot.git
cd PoeLeagueSpringBoot

# Compiler et lancer
mvn clean install
mvn spring-boot:run
```

L'application charge automatiquement les données depuis `src/main/resources/csv/InitPoeLeague.csv` au démarrage.

## 📄 Format du CSV d'initialisation

Le fichier `InitPoeLeague.csv` utilise le séparateur `;` avec 3 types de lignes :

```csv
LEAGUE;<nom>;<date_debut dd/MM/yyyy>;<date_fin dd/MM/yyyy ou vide>;
CHALLENGE;<nom>;<description>;<reward_points>;
PERSONNAGE;<position>;<pseudo>;<build>;<level>
```

**Exemple** :

```csv
LEAGUE;Crucible;07/04/2023;;
CHALLENGE;Beginner's Basics;Complete these basic tasks;3;
PERSONNAGE;1;PlayerOne;PATHFINDER;95
```

Les Challenges et Personnages qui suivent une ligne LEAGUE sont automatiquement associés à cette league.

## 🎯 Fonctionnalités métier principales

### `findBestChallenge()`
Retourne le challenge avec le plus de points de récompense.

### `findRewardPointsByLeague()`
Retourne une `Map<League, Integer>` avec la somme des reward points par league.
> Utilise une requête `JOIN FETCH` optimisée pour éviter le N+1.

### `findTopThreeBestBuild(League league)`
Retourne les 3 builds (spécialités) les plus populaires pour une league donnée.
> Implémentée en **native query** avec `GROUP BY` et `LIMIT 3`.

### `findBestBuildByLeague()`
Retourne les 3 meilleurs builds pour chaque league (approche semi-code / semi-requête).

### `initialisation()`
Charge les données depuis le CSV et les persiste en base dans une transaction unique (rollback en cas d'erreur).

## 🧪 Tests

```bash
mvn test
```

## 🎮 Builds PoE supportés (LabySpecialite)

`SLAYER`, `GLADIATOR`, `CHAMPION`, `ASSASSIN`, `SABOTEUR`, `TRICKSTER`, `JUGGERNAUT`, `BERSERKER`, `CHIEFTAIN`, `NECROMANCER`, `OCCULTIST`, `ELEMENTALIST`, `DEADEYE`, `RAIDER`, `PATHFINDER`, `INQUISITOR`, `HIEROPHANT`, `GUARDIAN`, `ASCENDANT`

## 📝 Notes techniques

- La colonne `description` remplace `desc` car ce dernier est un mot-clé réservé PostgreSQL
- Les collections `lesChallenges` et `classement` sont exposées en `unmodifiable` pour préserver l'encapsulation
- Le tri des challenges par reward points décroissant est appliqué dans le getter (préservé après reload Hibernate)
- L'initialisation utilise `@Transactional(rollbackOn = Exception.class)` pour garantir la cohérence

## 👤 Auteur

Tanguy Le Buhé

## 📜 Licence

Projet réalisé dans le cadre d'un TP Java Avancé.
