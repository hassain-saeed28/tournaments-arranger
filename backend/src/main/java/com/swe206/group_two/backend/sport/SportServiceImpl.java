package com.swe206.group_two.backend.sport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportServiceImpl implements SportService {
    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getAllSports() {
        List<Sport> sports = new ArrayList<>();
        sportRepository.findAll().forEach(sports::add);
        return sports;
    }

    public Optional<Sport> getSportById(Integer id) {
        return sportRepository.findById(id);
    }

    public List<Sport> getSportByNameContaining(String name) {
        return sportRepository.findByNameContaining(name);
    }

    public Sport createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    public Sport updateSportById(Integer id, Sport sport) {
        Sport _sport = sportRepository.findById(id).get();
        _sport.setName(sport.getName());
        return sportRepository.save(_sport);
    }

    @Override
    public void deleteAllSports() {
        sportRepository.deleteAll();
    }

    @Override
    public void deleteSportById(Integer id) {
        sportRepository.deleteById(id);
    }
}
