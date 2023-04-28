package com.swe206.group_two.backend.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe206.group_two.backend.participant.Participant;
import com.swe206.group_two.backend.participant.ParticipantRepository;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ParticipantRepository participantRepository;

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
    public Team createTeam(Team team, List<Integer> usersIds) {
        Team _team = teamRepository.save(team);
        for (Integer userId : usersIds) {
            participantRepository.save(new Participant(
                    userId, team.getTournamentId(), team.getId(), null));
        }
        return _team;
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
