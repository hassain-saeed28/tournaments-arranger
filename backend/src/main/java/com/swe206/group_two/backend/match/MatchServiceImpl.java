package com.swe206.group_two.backend.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Override
    public List<Match> getAllMatchs() {
        List<Match> matches = new ArrayList<>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    @Override
    public Optional<Match> getMatchById(Integer id) {
        return matchRepository.findById(id);
    }

    @Override
    public List<Match> getMatchesByTournamentId(Integer tournamentId) {
        return matchRepository.findAllByTournamentId(tournamentId);
    }

    @Override
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public void deleteAllMatches() {
        matchRepository.deleteAll();
    }

    @Override
    public void deleteMatchById(Integer id) {
        matchRepository.deleteById(id);
    }

    @Override
    public void changeMatchPoints(Integer points) {
        matchRepository.setPoints(points);
    }
}
