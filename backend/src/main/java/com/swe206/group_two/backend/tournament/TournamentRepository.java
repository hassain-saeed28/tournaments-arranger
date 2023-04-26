package com.swe206.group_two.backend.tournament;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    @Override
    public abstract List<Tournament> findAll();

    @Override
    public abstract Optional<Tournament> findById(Integer id);

    @Override
    public abstract <S extends Tournament> S save(S Tournament);

    @Override
    public abstract void deleteAll();

    @Override
    public abstract void deleteById(Integer id);
}
