package com.swe206.group_two.backend.team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swe206.group_two.backend.participant.Participant;
import com.swe206.group_two.backend.participant.ParticipantServiceImpl;
import com.swe206.group_two.backend.utils.JsonMappers;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamServiceImpl teamServiceImpl;
    private final ParticipantServiceImpl participantServiceImpl;

    public TeamController(TeamServiceImpl teamServiceImpl,
            ParticipantServiceImpl participantServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
        this.participantServiceImpl = participantServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Team>>> getAllTeams() {
        try {
            List<Team> teams = teamServiceImpl.getAllTeams();

            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                Map<String, List<Team>> map = new HashMap<>();
                map.put("data", teams);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> getTeamById(@PathVariable("id") Integer id) {
        try {
            Optional<Team> _team = teamServiceImpl.getTeamById(id);

            if (_team.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Team team = _team.get();
                JsonMappers json = new JsonMappers(team);
                json.put("participants", participantServiceImpl
                        .getAllParticipantsByTournamentId(id));
                return new ResponseEntity<>(json.getJson(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> teamSwap(@PathVariable("id") int id,
            @RequestBody TeamSwapDTO teamSwapDTO) {
        try {
            List<Participant> participants = participantServiceImpl
                    .getAllParticipantsByTeamId(id);
            if (participants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                for (Participant participant : participants) {
                    if (participant.getUserId().equals(teamSwapDTO.getOldUser())) {
                        participant.setUserId(teamSwapDTO.getNewUser());
                        participantServiceImpl.createParticipant(participant);
                    }
                }
                Map<String, List<Participant>> map = new HashMap<>();
                map.put("data", participantServiceImpl
                        .getAllParticipantsByTeamId(id));
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTeams() {
        try {
            List<Team> Teams = teamServiceImpl.getAllTeams();

            if (Teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                teamServiceImpl.deleteAllTeams();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTeamById(
            @PathVariable("id") Integer id) {
        try {
            Optional<Team> Team = teamServiceImpl.getTeamById(id);

            if (Team.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                teamServiceImpl.deleteTeamById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
