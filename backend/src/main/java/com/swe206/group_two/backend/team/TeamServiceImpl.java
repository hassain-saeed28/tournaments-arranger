package com.swe206.group_two.backend.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe206.group_two.backend.email.EmailServiceImpl;
import com.swe206.group_two.backend.participant.Participant;
import com.swe206.group_two.backend.participant.ParticipantServiceImpl;
import com.swe206.group_two.backend.tournament.TournamentServiceImpl;
import com.swe206.group_two.backend.user.UserServiceImpl;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private ParticipantServiceImpl participantServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private TournamentServiceImpl tournamentServiceImpl;

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
            participantServiceImpl.createParticipant(new Participant(
                    userId, team.getTournamentId(), team.getId(), null));
            emailServiceImpl.sendConfirmationMail(
                    userServiceImpl.getUserById(userId).get().getEmail(),
                    tournamentServiceImpl.getTournamentById(
                            team.getTournamentId()).get().getName());
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
