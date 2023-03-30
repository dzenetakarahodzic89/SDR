package ba.com.zira.sdr.core.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.model.User;
import ba.com.zira.sdr.api.model.battle.BattleLog;
import ba.com.zira.sdr.api.model.battle.BattleLogBattleResult;
import ba.com.zira.sdr.api.model.battle.BattleLogEntry;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.CountryState;
import ba.com.zira.sdr.api.model.battle.MapState;
import ba.com.zira.sdr.api.model.battle.SongStructure;
import ba.com.zira.sdr.api.model.battle.TeamStructure;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelations;
import ba.com.zira.sdr.dao.BattleTurnDAO;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.CountryRelationsDAO;

@Service
public class MusicRiskAIBattleHelper {
    private static CountryRelationsDAO countryRelationsDAO;
    private static CountryDAO countryDAO;
    private static BattleTurnDAO battleTurnDAO;
    private static SecureRandom rand;
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicRiskAIBattleHelper.class);
    private static final User systemUser = new User("System");
    private static BattleLog battleLog;
    private static MapState mapState;
    private static MapState newMapState;
    private static TeamsState teamsState;
    private static List<Long> conqueredTeamsIds;
    private static List<TeamStructure> alteredActiveNpcTeams;
    private static Long turnNumber;
    private static N2bObjectMapper mapper = new N2bObjectMapper();
    private static LookupService lookupService;

    public static Boolean simulateBattle(TeamStructure teamA, TeamStructure teamB, Long attackedCountryId) {
        var turnHistory = battleLog.getTurnHistory();
        var textHistory = battleLog.getTextHistory();
        var lastKey = getMaxKeyOfMap(textHistory);
        var battleResult = battleLog.getBattleResults();

        /** Choose a random artist from team A **/
        var artistA = teamA.getTeamArtists().get(rand.nextInt(teamA.getTeamArtists().size()));
        var songsA = artistA.getSongs();
        Collections.shuffle(songsA);

        /** Choose a random artist from team B **/
        var artistB = teamB.getTeamArtists().get(rand.nextInt(teamB.getTeamArtists().size()));
        var songsB = artistB.getSongs();
        Collections.shuffle(songsB);

        LOGGER.info("Attack is led by {}, defense is led by {}.", artistA.getName(), artistB.getName());
        textHistory.put(lastKey + 1, String.format("The battle is between %s and %s.", artistA.getName(), artistB.getName()));
        lastKey += 1;

        var numberOfWins = 0;

        /**
         * Each pair of songs from artists A and B compete. The deciding factor
         * is a random number between 0 and 100: value less than 51 means the
         * winner is song A, value greater than 50 means the winner is song B.
         **/
        for (var i = 0; i < songsA.size(); i++) {
            var songA = songsA.get(i);
            var songB = songsB.get(i);

            LOGGER.info("Song {} competes with song {}.", songA.getName(), songB.getName());
            if (rand.nextInt(101) <= 50) {
                LOGGER.info("Song {} wins round {}.", songA.getName(), i + 1);
                numberOfWins += 1;
                turnHistory.add(new BattleLogEntry(songA.getSongId(), songB.getSongId(), songA.getName(), songB.getName(),
                        songA.getSpotifyId(), songB.getSpotifyId(), songA.getAudioUrl(), songB.getAudioUrl(), songA.getSongId(),
                        songB.getSongId(), systemUser.getUserId()));
                textHistory.put(lastKey + 1,
                        String.format("Song %s wins over %s decided by %s.", songA.getName(), songB.getName(), systemUser.getUserId()));
            } else {
                LOGGER.info("Song {} wins round {}.", songB.getName(), i + 1);
                turnHistory.add(new BattleLogEntry(songA.getSongId(), songB.getSongId(), songA.getName(), songB.getName(),
                        songA.getSpotifyId(), songB.getSpotifyId(), songA.getAudioUrl(), songB.getAudioUrl(), songB.getSongId(),
                        songA.getSongId(), systemUser.getUserId()));
                textHistory.put(lastKey + 1,
                        String.format("Song %s wins over %s decided by %s.", songB.getName(), songA.getName(), systemUser.getUserId()));

            }
            lastKey += 1;

        }

        /**
         * If the attacker team won, generate new team formations, otherwise
         * keep team structures the same.
         **/
        if (numberOfWins > Math.floor(songsA.size() / 2.)) {
            LOGGER.info("The attacker team has won the battle!");
            restructureTeams(teamA, teamB, attackedCountryId);

            battleResult.add(new BattleLogBattleResult(turnNumber, teamA.getId(), teamB.getId(), teamA.getEligibleCountryIds(),
                    teamB.getEligibleCountryIds()));

            battleLog.setBattleResults(battleResult);
            battleLog.setTextHistory(textHistory);
            battleLog.setTurnHistory(turnHistory);
            return Boolean.TRUE;
        } else {
            LOGGER.info("The attacker team has lost the battle!");
            battleResult.add(new BattleLogBattleResult(turnNumber, teamB.getId(), teamA.getId(), teamB.getEligibleCountryIds(),
                    teamA.getEligibleCountryIds()));
            battleLog.setBattleResults(battleResult);
            battleLog.setTextHistory(textHistory);
            battleLog.setTurnHistory(turnHistory);
            return Boolean.FALSE;
        }

    }

    public static BattleSingleResponse simulateTurnForAI(BattleSingleResponse turn) {
        MusicRiskAIBattleHelper.mapState = turn.getMapState();

        MusicRiskAIBattleHelper.turnNumber = turn.getTurn();

        /** Copy mapState of last turn **/
        MusicRiskAIBattleHelper.newMapState = new MapState();
        MusicRiskAIBattleHelper.newMapState.setCountries(mapState.getCountries());
        MusicRiskAIBattleHelper.newMapState.setNumberOfActiveCountries(mapState.getNumberOfActiveCountries());
        MusicRiskAIBattleHelper.newMapState.setNumberOfActiveNpcTeams(mapState.getNumberOfActiveNpcTeams());
        MusicRiskAIBattleHelper.newMapState.setNumberOfActivePlayerTeams(mapState.getNumberOfActivePlayerTeams());
        MusicRiskAIBattleHelper.newMapState.setNumberOfInactiveCountries(mapState.getNumberOfInactiveCountries());
        MusicRiskAIBattleHelper.newMapState.setNumberOfPassiveCountries(mapState.getNumberOfPassiveCountries());

        MusicRiskAIBattleHelper.teamsState = turn.getTeamState();

        /** Copy teamsState of last turn **/
        var newTeamsState = new TeamsState();
        MusicRiskAIBattleHelper.conqueredTeamsIds = new ArrayList<>();
        MusicRiskAIBattleHelper.alteredActiveNpcTeams = new ArrayList<>();
        newTeamsState.setActivePlayerTeam(teamsState.getActivePlayerTeam());
        newTeamsState.setInactiveNpcTeams(teamsState.getInactiveNpcTeams());

        /** Copy turnCombatState of last turn **/
        var turnCombatState = turn.getTurnCombatState();
        MusicRiskAIBattleHelper.battleLog = turnCombatState.getBattleLogs().get(turnCombatState.getBattleLogs().size() - 1);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        /**
         * Simulate attack for each active NPC Team, excluding those conquered
         * in the process
         **/
        MusicRiskAIBattleHelper.teamsState.getActiveNpcTeams().forEach(npcTeam -> {
            if (!conqueredTeamsIds.contains(npcTeam.getId())) {
                var alteredTeam = alteredActiveNpcTeams.stream().filter(team -> Objects.equals(team.getId(), npcTeam.getId())).findFirst();
                if (alteredTeam.isPresent()) {
                    simulateAttack(alteredTeam.get());
                } else {
                    simulateAttack(npcTeam);
                }

            }
        });

        /** Update mapState, teamState and turnCombatState of the turn **/
        turn.setMapState(newMapState);
        String mapStateJSON;
        try {
            mapStateJSON = mapper.writeValueAsString(newMapState);
            turn.setMapStateJson(mapStateJSON);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error mapping mapState object to JSON. Exception message: {}", e.getMessage());
        }

        teamsState.setActiveNpcTeams(alteredActiveNpcTeams);
        turn.setTeamState(teamsState);
        try {
            String teamsStateJSON;
            teamsStateJSON = mapper.writeValueAsString(teamsState);
            turn.setTeamStateJson(teamsStateJSON);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error mapping teamsState object to JSON. Exception message: {}", e.getMessage());
        }

        var logs = turnCombatState.getBattleLogs().subList(0, turnCombatState.getBattleLogs().size());
        logs.add(battleLog);
        turnCombatState.setBattleLogs(logs);
        turn.setTurnCombatState(turnCombatState);

        LOGGER.info("End of turn {}.", turnNumber);
        return turn;
    }

    private static void simulateAttack(TeamStructure attackerTeam) {
        var textHistory = battleLog.getTextHistory();
        var lastKey = getMaxKeyOfMap(textHistory);

        /** Choose a random country the attacker team owns to attack from **/
        var attackLocation = countryDAO
                .findByPK(attackerTeam.getEligibleCountryIds().get(rand.nextInt(attackerTeam.getEligibleCountryIds().size())));

        LOGGER.info("Team with id {} launches an attack from {}!", attackerTeam.getId(), attackLocation.getName());
        /**
         * Choose a country to attack as a random country the country that the
         * attack is being launched from is in relation with
         **/
        var attackLocationRelations = countryRelationsDAO.getCountryRelations(attackLocation.getId());
        try {
            var countriesEligibleToAttack = mapper.readValue(attackLocationRelations, CountryRelations.class).getCountryRelations().stream()
                    .filter(c -> !attackerTeam.getEligibleCountryIds().contains(c.getCountryId())).collect(Collectors.toList());
            var locationThatIsBeingAttacked = countriesEligibleToAttack.get(rand.nextInt(countriesEligibleToAttack.size()));

            LOGGER.info("Country {} is being attacked!", locationThatIsBeingAttacked.getCountryName());

            var locationThatIsBeingAttackedStateOptional = mapState.getCountries().stream()
                    .filter(c -> Objects.equals(c.getCountryId(), locationThatIsBeingAttacked.getCountryId())).findAny();
            var attackerLocationStateOptional = mapState.getCountries().stream()
                    .filter(c -> Objects.equals(c.getCountryId(), attackLocation.getId())).findAny();

            if (locationThatIsBeingAttackedStateOptional.isPresent() && attackerLocationStateOptional.isPresent()) {
                var locationThatIsBeingAttackedState = locationThatIsBeingAttackedStateOptional.get();
                var attackerLocationState = attackerLocationStateOptional.get();

                /** Log the beginning of the attack **/
                textHistory.put(lastKey + 1, String.format("Country %s is starting to attack %s!", attackerLocationState.getCountryName(),
                        locationThatIsBeingAttackedState.getCountryName()));

                if (locationThatIsBeingAttackedState.getCountryStatus().equals("Passive")) {
                    LOGGER.info("Country {} is passive.", locationThatIsBeingAttackedState.getCountryName());
                    handlePassiveCountryAttack(attackerTeam, locationThatIsBeingAttackedState, attackerLocationState);
                } else {
                    handleActiveCountryAttack(attackerTeam, locationThatIsBeingAttackedState, attackerLocationState);
                }

            } else {
                LOGGER.error("Can't find country {} or {} in map state!", locationThatIsBeingAttacked.getCountryName(),
                        attackLocation.getName());

            }

        } catch (Exception e) {
            LOGGER.error("Error mapping JSON to object! Exception message: {}", e.getMessage());

        }

    }

    private static Long getMaxKeyOfMap(Map<Long, ?> map) {
        var optional = map.entrySet().stream().max((e1, e2) -> e1.getKey() >= e2.getKey() ? 1 : -1);
        if (optional.isPresent()) {
            return optional.get().getKey();
        }
        return 0L;
    }

    private static void handlePassiveCountryAttack(TeamStructure attackerTeam, CountryState passiveCountryState,
            CountryState attackerLocationCountryState) {
        /**
         * Add attacked country to eligible countries of attacker team. Update
         * number of wins for the attacker team.
         **/
        var newNpcTeam = new TeamStructure();
        newNpcTeam.setCountryId(attackerTeam.getCountryId());
        newNpcTeam.setCountryName(attackerTeam.getCountryName());
        var eligibleIds = attackerTeam.getEligibleCountryIds();
        eligibleIds.add(passiveCountryState.getCountryId());
        newNpcTeam.setEligibleCountryIds(eligibleIds);
        newNpcTeam.setId(attackerTeam.getId());
        newNpcTeam.setLastActiveTurn(attackerTeam.getLastActiveTurn());
        newNpcTeam.setNumberOfLoses(attackerTeam.getNumberOfLoses());
        newNpcTeam.setNumberOfWins(attackerTeam.getNumberOfWins() + 1);
        newNpcTeam.setTeamArtists(attackerTeam.getTeamArtists());
        alteredActiveNpcTeams.add(newNpcTeam);

        /**
         * Update team ownership and map value of the conquered passive country
         **/
        var mapStateCountries = newMapState.getCountries();
        mapStateCountries = mapStateCountries.stream().filter(c -> !Objects.equals(c.getCountryId(), passiveCountryState.getCountryId()))
                .collect(Collectors.toList());

        var conqueredCountryState = new CountryState();
        conqueredCountryState.setCountryId(passiveCountryState.getCountryId());
        conqueredCountryState.setCountryName(passiveCountryState.getCountryName());
        conqueredCountryState.setMapValue(attackerLocationCountryState.getMapValue());
        conqueredCountryState.setTeamOwnershipId(attackerTeam.getId());

        mapStateCountries.add(conqueredCountryState);
        newMapState.setCountries(mapStateCountries);

        /** Update battle logs **/
        var textHistory = battleLog.getTextHistory();
        var lastKey = getMaxKeyOfMap(textHistory);

        textHistory.put(lastKey + 1, String.format("Country %s is passive, %s has taken over!", passiveCountryState.getCountryName(),
                attackerLocationCountryState.getCountryName()));
        battleLog.setTextHistory(textHistory);
    }

    private static void handleActiveCountryAttack(TeamStructure attackerTeam, CountryState locationThatIsBeingAttackedState,
            CountryState attackerLocationCountryState) {

        var defenderTeamOptional = teamsState.getActiveNpcTeams().stream()
                .filter(t -> Objects.equals(t.getId(), locationThatIsBeingAttackedState.getTeamOwnershipId())).findFirst();

        if (defenderTeamOptional.isPresent()) {
            LOGGER.info("Team with id {} is defending country {}.", defenderTeamOptional.get().getId(),
                    locationThatIsBeingAttackedState.getCountryName());
            var won = simulateBattle(attackerTeam, defenderTeamOptional.get(), locationThatIsBeingAttackedState.getCountryId());

            if (won.booleanValue()) {
                handleWin(attackerTeam, defenderTeamOptional.get(), locationThatIsBeingAttackedState,
                        attackerLocationCountryState.getMapValue());
            } else {
                handleDefeat(attackerTeam, defenderTeamOptional.get());
            }
        } else {
            LOGGER.error("Team of attacked country {} not found in active NPC teams!", locationThatIsBeingAttackedState.getCountryName());

        }

    }

    private static void restructureTeams(TeamStructure winnerTeam, TeamStructure loserTeam, Long loserCountryId) {
        /**
         * Mix artists from winner team and artists from loserCountry and choose
         * the required number of artists from the resulting artist pool as
         * members of the winner team
         **/
        var artistPool = winnerTeam.getTeamArtists();
        artistPool.addAll(loserTeam.getTeamArtists().stream().filter(ta -> Objects.equals(ta.getCountryId(), loserCountryId))
                .collect(Collectors.toList()));
        Collections.shuffle(artistPool);
        winnerTeam.setTeamArtists(artistPool.subList(0, winnerTeam.getTeamArtists().size()));

        var artistNames = winnerTeam.getTeamArtists().stream().map(ta -> ta.getName()).collect(Collectors.joining(", "));

        LOGGER.info("Team with id {} now consists of {}.", winnerTeam.getId(), artistNames);

        /**
         * Remove artists of loser country from the loser team. Refill loser
         * team with artists from remaining countries.
         **/
        var loserTeamArtists = loserTeam.getTeamArtists().stream().filter(ta -> !Objects.equals(ta.getCountryId(), loserCountryId))
                .collect(Collectors.toList());
        var numberOfLostArtists = loserTeam.getTeamArtists().size() - loserTeamArtists.size();
        var requiredSongSize = loserTeamArtists.get(0).getSongs().size();
        var eligibleCountryIds = loserTeam.getEligibleCountryIds().stream()
                .filter(countryId -> !Objects.equals(countryId, loserCountryId) && !mapState.getCountries().stream()
                        .filter(c -> Objects.equals(c.getCountryId(), countryId) && c.getCountryStatus().equals("Active"))
                        .collect(Collectors.toList()).isEmpty())
                .collect(Collectors.toList());

        LOGGER.info("Team with id {} has lost {} artists.", loserTeam.getId(), numberOfLostArtists);

        if (!eligibleCountryIds.isEmpty()) {
            var additionalArtists = battleTurnDAO.getEligibleArtistsInformationByCountryId(eligibleCountryIds, numberOfLostArtists);
            additionalArtists.forEach(artist -> {
                var songs = battleTurnDAO.getRandomSongsForArtist(artist.getArtistId(), requiredSongSize);
                lookupService.lookupAudio(songs, SongStructure::getSongId, SongStructure::setAudioUrl);
                artist.setSongs(songs);
            });
            loserTeamArtists.addAll(additionalArtists);
            loserTeam.setTeamArtists(loserTeamArtists);

            artistNames = loserTeamArtists.stream().map(ta -> ta.getName()).collect(Collectors.joining(", "));

            LOGGER.info("Team with id {} now consists of {}.", loserTeam.getId(), artistNames);
        }

    }

    private static void handleWin(TeamStructure winnerTeam, TeamStructure loserTeam, CountryState loserCountryState,
            Double winnerTeamMapValue) {
        /*
         * Add loser country to winner team. Update number of wins for winner
         * team.
         */
        var newNpcTeam = new TeamStructure();
        newNpcTeam.setCountryId(winnerTeam.getCountryId());
        newNpcTeam.setCountryName(winnerTeam.getCountryName());
        var eligibleIds = winnerTeam.getEligibleCountryIds();
        eligibleIds.add(loserCountryState.getCountryId());
        newNpcTeam.setEligibleCountryIds(eligibleIds);
        newNpcTeam.setId(winnerTeam.getId());
        newNpcTeam.setLastActiveTurn(winnerTeam.getLastActiveTurn());
        newNpcTeam.setNumberOfLoses(winnerTeam.getNumberOfLoses());
        newNpcTeam.setNumberOfWins(winnerTeam.getNumberOfWins() + 1);
        newNpcTeam.setTeamArtists(winnerTeam.getTeamArtists());
        alteredActiveNpcTeams.add(newNpcTeam);

        /* Update loser country teamOwnership and mapValue */
        var mapStateCountries = newMapState.getCountries();
        mapStateCountries = mapStateCountries.stream().filter(c -> !Objects.equals(c.getCountryId(), loserCountryState.getCountryId()))
                .collect(Collectors.toList());
        var newCountryState = new CountryState();
        newCountryState.setCountryId(loserCountryState.getCountryId());
        newCountryState.setCountryName(loserCountryState.getCountryName());
        newCountryState.setMapValue(winnerTeamMapValue);
        newCountryState.setTeamOwnershipId(winnerTeam.getId());

        mapStateCountries.add(newCountryState);
        newMapState.setCountries(mapStateCountries);

        eligibleIds = loserTeam.getEligibleCountryIds();
        eligibleIds.remove(loserCountryState.getCountryId());

        /*
         * If the team has lost all countries, it is removed from
         * activeNpcTeams. Otherwise, just the loser country is removed from
         * loser team.
         */
        if (eligibleIds.isEmpty()) {
            LOGGER.info("Team with id {} now has no countries. It is removed from active teams.", loserTeam.getId());
            newMapState.setNumberOfActiveNpcTeams(newMapState.getNumberOfActiveNpcTeams() - 1);
            conqueredTeamsIds.add(loserTeam.getId());

        } else {
            newNpcTeam = new TeamStructure();

            newNpcTeam.setCountryId(loserTeam.getCountryId());
            newNpcTeam.setCountryName(loserTeam.getCountryName());
            newNpcTeam.setEligibleCountryIds(eligibleIds);
            newNpcTeam.setId(loserTeam.getId());
            newNpcTeam.setLastActiveTurn(loserTeam.getLastActiveTurn());
            newNpcTeam.setNumberOfLoses(loserTeam.getNumberOfLoses() + 1);
            newNpcTeam.setNumberOfWins(loserTeam.getNumberOfWins());
            newNpcTeam.setTeamArtists(loserTeam.getTeamArtists());
            alteredActiveNpcTeams.add(newNpcTeam);
        }

    }

    private static void handleDefeat(TeamStructure loserTeam, TeamStructure winnerTeam) {
        /** Update number of losses for attacker team **/
        var newNpcTeam = new TeamStructure();
        newNpcTeam.setCountryId(loserTeam.getCountryId());
        newNpcTeam.setCountryName(loserTeam.getCountryName());
        newNpcTeam.setEligibleCountryIds(loserTeam.getEligibleCountryIds());
        newNpcTeam.setId(loserTeam.getId());
        newNpcTeam.setLastActiveTurn(loserTeam.getLastActiveTurn());
        newNpcTeam.setNumberOfLoses(loserTeam.getNumberOfLoses() + 1);
        newNpcTeam.setNumberOfWins(loserTeam.getNumberOfWins());
        newNpcTeam.setTeamArtists(loserTeam.getTeamArtists());
        alteredActiveNpcTeams.add(newNpcTeam);

        /** Update number of wins for defender team **/
        newNpcTeam = new TeamStructure();
        newNpcTeam.setCountryId(winnerTeam.getCountryId());
        newNpcTeam.setCountryName(winnerTeam.getCountryName());
        newNpcTeam.setEligibleCountryIds(winnerTeam.getEligibleCountryIds());
        newNpcTeam.setId(winnerTeam.getId());
        newNpcTeam.setLastActiveTurn(winnerTeam.getLastActiveTurn());
        newNpcTeam.setNumberOfLoses(winnerTeam.getNumberOfLoses());
        newNpcTeam.setNumberOfWins(winnerTeam.getNumberOfWins() + 1);
        newNpcTeam.setTeamArtists(winnerTeam.getTeamArtists());
        alteredActiveNpcTeams.add(newNpcTeam);

    }

    public BattleSingleResponse differeniateAITeamIds(BattleSingleResponse turn) {
        var turnTeamState = turn.getTeamState();
        var turnMapState = turn.getMapState();
        var newCountryStates = new ArrayList<CountryState>();

        var id = 2L;
        Double segment = 2. / (turnTeamState.getActiveNpcTeams().size() + 1);

        /**
         * Assign id for each active NPC team starting from 2. Update
         * teamOwnership and mapValue for each active NPC team's country.
         * MapValue is a value in range from -1 to 1, each team gets a unique
         * value. Player team gets mapValue 1.
         */
        for (var i = 0; i < turnTeamState.getActiveNpcTeams().size(); i++) {
            var team = turnTeamState.getActiveNpcTeams().get(i);
            team.setId(id);
            var mapValue = -1 + segment * (i + 1);
            var teamCountries = turnMapState.getCountries().stream().filter(c -> team.getEligibleCountryIds().contains(c.getCountryId()))
                    .collect(Collectors.toList());
            teamCountries.forEach(country -> {
                country.setTeamOwnershipId(team.getId());
                country.setMapValue(mapValue);
            });
            newCountryStates.addAll(teamCountries);
            id++;
        }

        var playerTeamCountries = turnMapState.getCountries().stream()
                .filter(c -> turnTeamState.getActivePlayerTeam().getEligibleCountryIds().contains(c.getCountryId()))
                .collect(Collectors.toList());
        playerTeamCountries.forEach(country -> {
            country.setTeamOwnershipId(1L);
            country.setMapValue(1.);
        });
        newCountryStates.addAll(playerTeamCountries);

        newCountryStates.addAll(turnMapState.getCountries().stream().filter(
                c -> !newCountryStates.stream().map(c2 -> c2.getCountryId()).collect(Collectors.toList()).contains(c.getCountryId()))
                .collect(Collectors.toList()));
        turnMapState.setCountries(newCountryStates);

        turn.setTeamState(turnTeamState);
        turn.setMapState(turnMapState);
        return turn;
    }

}
