package com.swe206.group_two.backend.team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    public abstract List<Team> getAllTeams();

    public abstract Optional<Team> getTeamById(Integer id);

    public abstract List<Team> getTeamsByTournamentId(Integer tournamentId);

    public abstract Team createTeam(Team team);

    public abstract void deleteAllTeams();

    public abstract void deleteTeamById(Integer id);
}
