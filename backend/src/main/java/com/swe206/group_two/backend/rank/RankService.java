package com.swe206.group_two.backend.rank;

import java.util.List;
import java.util.Optional;

public interface RankService {
    public abstract List<Rank> getAllRanks();

    public abstract Optional<Rank> getRankById(Integer id);

    public abstract List<Rank> getAllRanksByTournamentId(Integer id);

    public abstract Rank createRank(Rank rank);

    public abstract void deleteAllRanks();

    public abstract void deleteRankById(Integer id);
}
