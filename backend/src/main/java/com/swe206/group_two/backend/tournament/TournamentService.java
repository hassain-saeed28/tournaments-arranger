package com.swe206.group_two.backend.tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentService {
    public abstract List<Tournament> getAllTournaments();

    public abstract Optional<Tournament> getTournamentById(Integer id);

    public abstract Tournament createTournament(TournamentDTO tournament);

    // public abstract Tournament updateTournamentById(Integer id, TournamentDTO
    // tournamentDTO);

    public abstract void deleteAllTournament();

    public abstract void deleteTournamentById(Integer id);
}
