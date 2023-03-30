package ba.com.zira.sdr.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.model.User;
import ba.com.zira.sdr.api.model.battle.BattleLog;
import ba.com.zira.sdr.api.model.battle.BattleLogBattleResult;
import ba.com.zira.sdr.api.model.battle.BattleLogEntry;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.CountryState;
import ba.com.zira.sdr.api.model.battle.MapState;
import ba.com.zira.sdr.api.model.battle.TeamStructure;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelations;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.CountryRelationsDAO;

@Service
public class MusicRiskAIBattleHelper {
    private CountryRelationsDAO countryRelationsDAO;
    private CountryDAO countryDAO;
    private Random rand;
    private static final User systemUser = new User("System");

    public Boolean simulateBattle(TeamStructure teamA, TeamStructure teamB, Long countryA, Long countryB, BattleLog battleLog,
            Long textHistoryLastIndex, Long turnNumber) {
        var turnHistory = battleLog.getTurnHistory();
        var textHistory = battleLog.getTextHistory();
        var battleResult = battleLog.getBattleResults();

        var artistA = teamA.getTeamArtists().get(rand.nextInt(teamA.getTeamArtists().size()));
        var songsA = artistA.getSongs();
        Collections.shuffle(songsA);

        var artistB = teamB.getTeamArtists().get(rand.nextInt(teamB.getTeamArtists().size()));
        var songsB = artistB.getSongs();
        Collections.shuffle(songsB);

        textHistory.put(textHistoryLastIndex + 1, "The battle is between " + artistA.getName() + " and " + artistB.getName() + ".");
        textHistoryLastIndex += 1;
        var numberOfWins = 0;
        for (int i = 0; i < songsA.size(); i++) {
            var songA = songsA.get(i);
            var songB = songsB.get(i);

            if (rand.nextInt(101) <= 50) {
                numberOfWins += 1;
                turnHistory.add(new BattleLogEntry(songA.getSongId(), songB.getSongId(), songA.getName(), songB.getName(),
                        songA.getSpotifyId(), songB.getSpotifyId(), songA.getAudioUrl(), songB.getAudioUrl(), songA.getSongId(),
                        songB.getSongId(), systemUser.getUserId()));
                textHistory.put(textHistoryLastIndex + 1,
                        "Song " + songA.getName() + " wins over " + songB.getName() + " decided by " + systemUser.getUserId());
            } else {
                turnHistory.add(new BattleLogEntry(songA.getSongId(), songB.getSongId(), songA.getName(), songB.getName(),
                        songA.getSpotifyId(), songB.getSpotifyId(), songA.getAudioUrl(), songB.getAudioUrl(), songB.getSongId(),
                        songA.getSongId(), systemUser.getUserId()));
                textHistory.put(textHistoryLastIndex + 1,
                        "Song " + songB.getName() + " wins over " + songA.getName() + " decided by " + systemUser.getUserId());

            }
            textHistoryLastIndex += 1;

        }

        if (numberOfWins > Math.floor(songsA.size() / 2.)) {
            battleResult.add(new BattleLogBattleResult(turnNumber, teamA.getId(), teamB.getId(), teamA.getEligibleCountryIds(),
                    teamB.getEligibleCountryIds()));

            var artistPool = teamA.getTeamArtists();
            artistPool.addAll(teamB.getTeamArtists());
            Collections.shuffle(artistPool);
            teamA.setTeamArtists(artistPool.subList(0, teamA.getTeamArtists().size()));
            battleLog.setBattleResults(battleResult);
            battleLog.setTextHistory(textHistory);
            battleLog.setTurnHistory(turnHistory);
            return true;
        } else {
            battleResult.add(new BattleLogBattleResult(turnNumber, teamB.getId(), teamA.getId(), teamB.getEligibleCountryIds(),
                    teamA.getEligibleCountryIds()));
            battleLog.setBattleResults(battleResult);
            battleLog.setTextHistory(textHistory);
            battleLog.setTurnHistory(turnHistory);
            return false;
        }

        // Omogućava metodu simulateBattle(Team 1, Team 2) koja simulira bitku
        // između dva AI Active tima(države). Team 1 je napadač i ako pobijedi,
        // vrši se spajanje u jedan tim, i pjevači se spajaju i random se
        // restruktuira
        // ekipa tj ako je tim od 3, 3+3 = 6 i od tih 6 bira se 3. Pjesme ostaju
        // uvijek iste za pjevače.
        // Battle log treba isto dopuniti sa historijom bitke. Odabir pjesme je
        // random od 0 do 100,
        // 1 do 50 pjesma pjevača A, 51 do 100 pjevača B.
    }

    public BattleSingleResponse simulateTurnForAI(BattleSingleResponse turn) {
        var mapState = turn.getMapState();

        var newMapState = new MapState();
        newMapState.setCountries(mapState.getCountries());
        newMapState.setNumberOfActiveCountries(mapState.getNumberOfActiveCountries());
        newMapState.setNumberOfActiveNpcTeams(mapState.getNumberOfActiveNpcTeams());
        newMapState.setNumberOfActivePlayerTeams(mapState.getNumberOfActivePlayerTeams());
        newMapState.setNumberOfInactiveCountries(mapState.getNumberOfInactiveCountries());
        newMapState.setNumberOfPassiveCountries(mapState.getNumberOfPassiveCountries());

        var teamsState = turn.getTeamState();

        var newTeamsState = new TeamsState();
        var newNpcTeams = new ArrayList<TeamStructure>();
        var processedNpcTeams = new ArrayList<Long>();
        newTeamsState.setActivePlayerTeam(teamsState.getActivePlayerTeam());
        newTeamsState.setInactiveNpcTeams(teamsState.getInactiveNpcTeams());

        var turnCombatState = turn.getTurnCombatState();
        var battleLog = turnCombatState.getBattleLogs().get(turnCombatState.getBattleLogs().size() - 1);
        var textHistory = battleLog.getTextHistory();

        N2bObjectMapper mapper = new N2bObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        teamsState.getActiveNpcTeams().forEach(npcTeam -> {
            if (!processedNpcTeams.contains(npcTeam.getId())) {
                var lastKey = textHistory.entrySet().stream().max((e1, e2) -> e1.getKey() > e2.getKey() ? 1 : -1).get().getKey();

                // Šta ako se odabere država koja nema artista u timu?
                var attacker = countryDAO
                        .findByPK(npcTeam.getEligibleCountryIds().get(rand.nextInt(npcTeam.getEligibleCountryIds().size())));
                var countryRelations = countryRelationsDAO.getCountryRelations(attacker.getId());
                try {
                    var countriesEligibleToAttack = mapper.readValue(countryRelations, CountryRelations.class).getCountryRelations()
                            .stream().filter(c -> !npcTeam.getEligibleCountryIds().contains(c.getCountryId())).collect(Collectors.toList());
                    var defender = countriesEligibleToAttack.get(rand.nextInt(countriesEligibleToAttack.size()));

                    var defenderCountryState = mapState.getCountries().stream()
                            .filter(c -> Objects.equals(c.getCountryId(), defender.getCountryId())).findAny().get();

                    if (defenderCountryState.getCountryStatus().equals("Passive")) {
                        var newNpcTeam = new TeamStructure();
                        newNpcTeam.setCountryId(npcTeam.getCountryId());
                        newNpcTeam.setCountryName(npcTeam.getCountryName());
                        newNpcTeam.setEligibleCountryIds(npcTeam.getEligibleCountryIds());
                        newNpcTeam.setId(npcTeam.getId());
                        // Treba li azurirati last turn?
                        newNpcTeam.setLastActiveTurn(npcTeam.getLastActiveTurn());
                        newNpcTeam.setNumberOfLoses(npcTeam.getNumberOfLoses());
                        // Treba li povecati wins za 1, ako je osvojena passive
                        // drzava?
                        newNpcTeam.setNumberOfWins(npcTeam.getNumberOfWins() + 1);
                        newNpcTeam.setTeamArtists(npcTeam.getTeamArtists());
                        newNpcTeams.add(newNpcTeam);

                        // Update country team ownership.map value and status in
                        // map
                        // state
                        var mapStateCountries = newMapState.getCountries();
                        mapStateCountries = mapStateCountries.stream()
                                .filter(c -> !Objects.equals(c.getCountryId(), defender.getCountryId())).toList();
                        CountryState newCountryState = new CountryState();
                        newCountryState.setCountryId(defender.getCountryId());
                        newCountryState.setCountryName(defender.getCountryName());
                        newCountryState.setMapValue(0.);
                        newCountryState.setCountryStatus("Inactive");
                        newCountryState.setTeamOwnershipId(npcTeam.getId());

                        mapStateCountries.add(newCountryState);
                        newMapState.setCountries(mapStateCountries);
                        newMapState.setNumberOfInactiveCountries(newMapState.getNumberOfInactiveCountries() + 1);
                        newMapState.setNumberOfPassiveCountries(newMapState.getNumberOfPassiveCountries() - 1);

                        // Update battle logs
                        textHistory.put(lastKey + 1,
                                "Country " + attacker.getName() + " is starting to attack " + defender.getCountryName() + "!");
                        lastKey += 1;
                        textHistory.put(lastKey + 1,
                                "Country " + defender.getCountryName() + "id passive, " + attacker.getName() + " has taken over!");

                        battleLog.setTextHistory(textHistory);
                    } else {
                        textHistory.put(lastKey + 1,
                                "Country " + attacker.getName() + " is starting to attack " + defender.getCountryName() + "!");
                        battleLog.setTextHistory(textHistory);
                        lastKey += 1;

                        var defenderTeam = teamsState.getActiveNpcTeams().stream()
                                .filter(t -> Objects.equals(t.getId(), defenderCountryState.getTeamOwnershipId())).findFirst().get();
                        var won = simulateBattle(npcTeam, defenderTeam, attacker.getId(), defender.getCountryId(), battleLog, lastKey,
                                turn.getTurn());
                        if (!won) {
                            var newNpcTeam = new TeamStructure();
                            newNpcTeam.setCountryId(npcTeam.getCountryId());
                            newNpcTeam.setCountryName(npcTeam.getCountryName());
                            newNpcTeam.setEligibleCountryIds(npcTeam.getEligibleCountryIds());
                            newNpcTeam.setId(npcTeam.getId());
                            // Treba li azurirati last turn?
                            newNpcTeam.setLastActiveTurn(npcTeam.getLastActiveTurn());
                            newNpcTeam.setNumberOfLoses(npcTeam.getNumberOfLoses() + 1);
                            newNpcTeam.setNumberOfWins(npcTeam.getNumberOfWins());
                            newNpcTeam.setTeamArtists(npcTeam.getTeamArtists());
                            newNpcTeams.add(newNpcTeam);

                        } else {
                            var newNpcTeam = new TeamStructure();
                            newNpcTeam.setCountryId(npcTeam.getCountryId());
                            newNpcTeam.setCountryName(npcTeam.getCountryName());
                            var eligibleIds = npcTeam.getEligibleCountryIds();
                            eligibleIds.add(defender.getCountryId());
                            newNpcTeam.setEligibleCountryIds(eligibleIds);
                            newNpcTeam.setId(npcTeam.getId());
                            // Treba li azurirati last turn?
                            newNpcTeam.setLastActiveTurn(npcTeam.getLastActiveTurn());
                            newNpcTeam.setNumberOfLoses(npcTeam.getNumberOfLoses());

                            newNpcTeam.setNumberOfWins(npcTeam.getNumberOfWins() + 1);
                            newNpcTeam.setTeamArtists(npcTeam.getTeamArtists());
                            newNpcTeams.add(newNpcTeam);

                            // Update country team ownership,map value and
                            // status in
                            // map
                            // state
                            var mapStateCountries = newMapState.getCountries();
                            mapStateCountries = mapStateCountries.stream()
                                    .filter(c -> !Objects.equals(c.getCountryId(), defender.getCountryId())).toList();
                            CountryState newCountryState = new CountryState();
                            newCountryState.setCountryId(defender.getCountryId());
                            newCountryState.setCountryName(defender.getCountryName());
                            newCountryState.setMapValue(0.);
                            newCountryState.setCountryStatus("Inactive");
                            newCountryState.setTeamOwnershipId(npcTeam.getId());

                            mapStateCountries.add(newCountryState);
                            newMapState.setCountries(mapStateCountries);
                            newMapState.setNumberOfActiveNpcTeams(newMapState.getNumberOfActiveNpcTeams() - 1);
                            newMapState.setNumberOfInactiveCountries(newMapState.getNumberOfInactiveCountries() + 1);
                            newMapState.setNumberOfActiveCountries(newMapState.getNumberOfActiveCountries() - 1);

                            processedNpcTeams.add(defenderTeam.getId());
                        }
                    }

                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        });

        turn.setMapState(newMapState);
        String mapStateJSON;
        try {
            mapStateJSON = mapper.writeValueAsString(newMapState);
            turn.setMapStateJson(mapStateJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        teamsState.setActiveNpcTeams(newNpcTeams);
        turn.setTeamState(teamsState);
        try {
            String teamsStateJSON;
            teamsStateJSON = mapper.writeValueAsString(teamsState);
            turn.setTeamStateJson(teamsStateJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        var logs = turnCombatState.getBattleLogs().subList(0, turnCombatState.getBattleLogs().size());
        logs.add(battleLog);
        turnCombatState.setBattleLogs(logs);
        turn.setTurnCombatState(turnCombatState);

        return turn;

        // wrapper metoda za 1 metodu da probere
        // bitke shodno
        // county-realations. Napadati mogu samo države sa relacijama
        // (sat_country_relations). Svi timovi AI jednom pokušaju napasti.
        // Ako Tim 2 (odbrana) pobijedi ne spajaju se i ostaju timovi isti.

    }

    public void generateAITeams() {

        // ažurira team_state zadnjeg poteza da svi Active countries/teams su
        // zaseban broj ID-a (1 je uvijek player team, ostalo slobodno)
    }

}
