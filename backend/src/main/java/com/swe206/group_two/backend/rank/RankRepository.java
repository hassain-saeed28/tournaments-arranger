package com.swe206.group_two.backend.rank;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Integer> {
    @Override
    public abstract List<Rank> findAll();

    @Override
    public abstract Optional<Rank> findById(Integer id);

    public abstract List<Rank> findAllByTournamentId(Integer id);

    public abstract Rank findByParticipantId(Integer id);

    @Override
    public abstract <S extends Rank> S save(S rank);

    @Override
    public abstract void deleteAll();

    @Override
    public abstract void deleteById(Integer id);
}
