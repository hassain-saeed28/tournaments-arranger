package com.swe206.group_two.backend.match;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    public abstract List<Match> getAllMatches();

    public abstract Optional<Match> getMatchById(Integer id);

    public abstract List<Match> getMatchesByTournamentId(Integer tournamentId);

    public abstract Match createMatch(Match match);

    public abstract void deleteAllMatches();

    public abstract void deleteMatchById(Integer id);

    public abstract Match setMatchScoreById(Integer id,
            Integer firstParticipantScores, Integer secondParticipantScores);

    // public abstract List<Match> generateMatches();
}
