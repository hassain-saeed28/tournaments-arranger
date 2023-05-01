package com.swe206.group_two.backend.tournament;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swe206.group_two.backend.match.MatchServiceImpl;
import com.swe206.group_two.backend.participant.ParticipantServiceImpl;
import com.swe206.group_two.backend.rank.Rank;
import com.swe206.group_two.backend.rank.RankServiceImpl;
import com.swe206.group_two.backend.team.Team;
import com.swe206.group_two.backend.team.TeamDTO;
import com.swe206.group_two.backend.team.TeamServiceImpl;
import com.swe206.group_two.backend.utils.JsonMappers;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {
    private final TournamentServiceImpl tournamentServiceImpl;
    private final TeamServiceImpl teamServiceImpl;
    private final RankServiceImpl rankServiceImpl;
    private final ParticipantServiceImpl participantServiceImpl;
    private final MatchServiceImpl matchServiceImpl;

    public TournamentController(TournamentServiceImpl tournamentServiceImpl,
            TeamServiceImpl teamServiceImpl,
            RankServiceImpl rankServiceImpl,
            ParticipantServiceImpl participantServiceImpl,
            MatchServiceImpl matchServiceImpl) {
        this.tournamentServiceImpl = tournamentServiceImpl;
        this.teamServiceImpl = teamServiceImpl;
        this.rankServiceImpl = rankServiceImpl;
        this.participantServiceImpl = participantServiceImpl;
        this.matchServiceImpl = matchServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Tournament>>> getAllTournaments() {
        try {
            List<Tournament> tournaments = tournamentServiceImpl
                    .getAllTournaments();

            if (tournaments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                Map<String, List<Tournament>> map = new HashMap<>();
                map.put("data", tournaments);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTournamentById(
            @PathVariable("id") Integer id) {
        try {
            Optional<Tournament> _tournament = tournamentServiceImpl
                    .getTournamentById(id);

            if (_tournament.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Tournament tournament = _tournament.get();
                JsonMappers json = new JsonMappers(tournament);
                json.put("ranks", rankServiceImpl.getAllRanksByTournamentId(id));
                json.put("participants", participantServiceImpl.getAllParticipantsByTournamentId(id));
                json.put("matches", matchServiceImpl.getMatchesByTournamentId(id));
                return new ResponseEntity<>(json.getJson(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/teams")
    public ResponseEntity<Map<String, List<Team>>> getAllTeamsByTournamentId(
            @PathVariable("id") Integer id) {
        try {
            List<Team> teams = teamServiceImpl.getAllTeamsByTournamentId(id);

            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Map<String, List<Team>> map = new HashMap<>();
                map.put("data", teams);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tournament> createTournament(
            @RequestBody TournamentDTO tournamentDTO) {
        try {
            return new ResponseEntity<Tournament>(
                    tournamentServiceImpl.createTournament(tournamentDTO),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "{id}/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> createTeamByTournamentId(
            @PathVariable("id") Integer id,
            @RequestBody TeamDTO teamDTO) {
        try {
            Optional<Tournament> tournament = tournamentServiceImpl
                    .getTournamentById(id);

            if (tournament.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Rank rank = rankServiceImpl.createRank(
                        new Rank(id, null, null));
                return new ResponseEntity<>(
                        teamServiceImpl.createTeam(
                                new Team(teamDTO.getName(), id, rank.getId()),
                                teamDTO.getUsersIds()),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tournament> partialUpdateTournametn(
            @PathVariable("id") Integer id,
            @RequestBody TournamentDTO tournamentDTO) {
        try {
            Optional<Tournament> _tournament = tournamentServiceImpl.getTournamentById(id);

            if (_tournament.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(
                        tournamentServiceImpl.updateTournamentById(id, tournamentDTO),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTournaments() {
        try {
            List<Tournament> sports = tournamentServiceImpl
                    .getAllTournaments();

            if (sports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                tournamentServiceImpl.deleteAllTournament();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTournamentById(
            @PathVariable("id") Integer id) {
        try {
            Optional<Tournament> _sport = tournamentServiceImpl
                    .getTournamentById(id);

            if (_sport.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                tournamentServiceImpl.deleteTournamentById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
