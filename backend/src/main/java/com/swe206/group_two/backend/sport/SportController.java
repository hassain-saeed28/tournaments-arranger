package com.swe206.group_two.backend.sport;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sports")
public class SportController {
    private final SportServiceImpl sportServiceImpl;

    public SportController(SportServiceImpl sportServiceImpl) {
        this.sportServiceImpl = sportServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Sport>> getAllSports() {
        try {
            List<Sport> sports = sportServiceImpl.getAllSports();

            if (sports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(sports, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Sport> getSportById(@PathVariable("id") Integer id) {
        Optional<Sport> sport = sportServiceImpl.getSportById(id);

        if (sport.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(sport.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        try {
            return new ResponseEntity<Sport>(sportServiceImpl.createSport(sport), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Sport> updateSport(@PathVariable("id") Integer id,
            @RequestBody Sport sport) {
        Optional<Sport> sportData = sportServiceImpl.getSportById(id);

        if (sportData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(sportServiceImpl.updateSportById(id, sport), HttpStatus.OK);
        }
    }

    @DeleteMapping
    public void deleteAllSports() {
        sportServiceImpl.deleteAllSports();
    }

    @DeleteMapping("{id}")
    public void deleteSportsById(@PathVariable("id") Integer id) {
        sportServiceImpl.deleteSportById(id);
    }
}
