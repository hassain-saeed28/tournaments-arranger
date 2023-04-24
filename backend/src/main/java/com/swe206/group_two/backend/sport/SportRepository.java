package com.swe206.group_two.backend.sport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Integer> {
    @Override
    public abstract List<Sport> findAll();

    @Override
    public abstract Optional<Sport> findById(Integer id);

    public abstract List<Sport> findByNameContaining(String name);

    @Override
    public abstract <S extends Sport> S save(S sport);

    @Override
    public abstract void deleteAll();

    @Override
    public abstract void deleteById(Integer id);
}
