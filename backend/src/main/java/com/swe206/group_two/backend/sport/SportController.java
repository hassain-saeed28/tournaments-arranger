package com.swe206.group_two.backend.sport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sports")
public class SportController {
    private final SportServiceImpl sportServiceImpl;

    public SportController(SportServiceImpl sportServiceImpl) {
        this.sportServiceImpl = sportServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Sport>>> getAllSports(
            @RequestParam(name ="name", required = false) String name) {
        try {
            List<Sport> sports;
            if (name == null) {
                sports = sportServiceImpl.getAllSports();
            } else {
                sports = sportServiceImpl.getSportByNameContaining(name);
            }

            if (sports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                Map<String, List<Sport>> map = new HashMap<>();
                map.put("data", sports);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Sport> getSportById(@PathVariable("id") Integer id) {
        try {
            Optional<Sport> sport = sportServiceImpl.getSportById(id);

            if (sport.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(sport.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        try {
            return new ResponseEntity<Sport>(
                    sportServiceImpl.createSport(sport), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sport> updateSport(@PathVariable("id") Integer id,
            @RequestBody Sport sport) {
        try {
            Optional<Sport> _sport = sportServiceImpl.getSportById(id);

            if (_sport.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(
                        sportServiceImpl.updateSportById(id, sport),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllSports() {
        try {
            List<Sport> sports = sportServiceImpl.getAllSports();

            if (sports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                sportServiceImpl.deleteAllSports();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteSportById(
            @PathVariable("id") Integer id) {
        try {
            Optional<Sport> sport = sportServiceImpl.getSportById(id);

            if (sport.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                sportServiceImpl.deleteSportById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
