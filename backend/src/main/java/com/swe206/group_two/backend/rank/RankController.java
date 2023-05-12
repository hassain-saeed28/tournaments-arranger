package com.swe206.group_two.backend.rank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranks")
public class RankController {
    private final RankServiceImpl rankServiceImpl;

    public RankController(RankServiceImpl rankServiceImpl) {
        this.rankServiceImpl = rankServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Rank>>> getAllRanks() {
        try {
            List<Rank> ranks = rankServiceImpl.getAllRanks();

            if (ranks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                Map<String, List<Rank>> map = new HashMap<>();
                map.put("data", ranks);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Rank> getRankById(@PathVariable("id") Integer id) {
        try {
            Optional<Rank> ranks = rankServiceImpl.getRankById(id);

            if (ranks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(ranks.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllRanks() {
        try {
            List<Rank> ranks = rankServiceImpl.getAllRanks();

            if (ranks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                rankServiceImpl.deleteAllRanks();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteRankById(
            @PathVariable("id") Integer id) {
        try {
            Optional<Rank> ranks = rankServiceImpl.getRankById(id);

            if (ranks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                rankServiceImpl.deleteRankById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
