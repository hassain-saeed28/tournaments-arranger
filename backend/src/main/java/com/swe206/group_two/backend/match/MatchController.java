package com.swe206.group_two.backend.match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchServiceImpl matchServiceImpl;

    public MatchController(MatchServiceImpl matchServiceImpl) {
        this.matchServiceImpl = matchServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Match>>> getAllMatches() {
        try {
            List<Match> matches = matchServiceImpl.getAllMatches();

            if (matches.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                Map<String, List<Match>> map = new HashMap<>();
                map.put("data", matches);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Match> updateMatchScores(
            @PathVariable("id") Integer id, @RequestBody MatchDTO matchDTO) {
        Optional<Match> _match = matchServiceImpl.getMatchById(id);

        if (_match.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Match match = matchServiceImpl.setMatchScoreById(id,
                    matchDTO.getFirstParticipantScores(),
                    matchDTO.getSecondParticipantScores());
            return new ResponseEntity<Match>(match, HttpStatus.OK);
        }
    }
}
