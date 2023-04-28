package com.swe206.group_two.backend.match;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    @Override
    public abstract List<Match> findAll();

    @Override
    public abstract Optional<Match> findById(Integer id);

    public abstract List<Match> findAllByTournamentId(Integer tournamentId);

    @Override
    public abstract <S extends Match> S save(S match);

    @Override
    public abstract void deleteAll();

    @Override
    public abstract void deleteById(Integer id);

    // public abstract void setPoints(Integer points);
}
