package com.swe206.group_two.backend.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
                    json.put("participants",
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
