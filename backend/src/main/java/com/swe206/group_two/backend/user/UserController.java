package com.swe206.group_two.backend.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swe206.group_two.backend.participant.Participant;
import com.swe206.group_two.backend.participant.ParticipantServiceImpl;
import com.swe206.group_two.backend.rank.Rank;
import com.swe206.group_two.backend.rank.RankServiceImpl;
import com.swe206.group_two.backend.utils.JsonMappers;
import com.swe206.group_two.backend.utils.UserRole;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    private final ParticipantServiceImpl participantServiceImpl;

    private final RankServiceImpl rankServiceImpl;

    public UserController(UserServiceImpl userServiceImpl,
            ParticipantServiceImpl participantServiceImpl,
            RankServiceImpl rankServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.participantServiceImpl = participantServiceImpl;
        this.rankServiceImpl = rankServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(name = "tournamentId", required = false) Integer id) {
        List<User> students = new ArrayList<>(userServiceImpl.getAllUsers().stream()
                .filter(user -> user.getRole().equals(UserRole.Student)).toList());
        if (id != null) {
            List<Participant> participants = participantServiceImpl
                    .getAllParticipantsByTournamentId(id);
            for (Participant participant : participants) {
                for (int j = 0; j < students.size(); j++) {
                    if (participant.getUserId()
                            .equals(students.get(j).getId())) {
                        students.remove(j);
                        j--;
                    }
                }
            }
        }

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Map<String, List<User>> map = new HashMap<>();
            map.put("data", students);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> getUserProfile(
            @PathVariable("id") Integer id) {
        try {
            Optional<User> _user = userServiceImpl.getUserById(id);

            if (_user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                User user = _user.get();
                JsonMappers json = new JsonMappers(user);
                if (user.getRole().equals(UserRole.Student)) {
                    List<Participant> participants = participantServiceImpl
                            .getAllParticipantByUserId(id);
                    json.put("tournamentParticipants",
                            participants);

                    List<Rank> ranks = new ArrayList<>();
                    for (Participant participant : participants) {
                        ranks.add(rankServiceImpl
                                .getRankByParticipantId(participant.getId()));
                    }
                    json.put("ranks", ranks);
                }
                return new ResponseEntity<>(json.getJson(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
