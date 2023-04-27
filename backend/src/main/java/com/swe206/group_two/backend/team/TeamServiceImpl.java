package com.swe206.group_two.backend.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    @Override
    public Optional<Team> getTeamById(Integer id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> getAllTeamsByTournamentId(Integer touramentId) {
        return teamRepository.findAllByTournamentId(touramentId);
    }

    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteAllTeams() {
        teamRepository.deleteAll();
    }

    @Override
    public void deleteTeamById(Integer id) {
        teamRepository.deleteById(id);
    }
}
