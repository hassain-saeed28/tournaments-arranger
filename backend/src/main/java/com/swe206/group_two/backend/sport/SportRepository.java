package com.swe206.group_two.backend.sport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Integer> {
    public abstract List<Sport> findAll();

    public abstract Optional<Sport> findById(Integer id);

    public abstract List<Sport> getSportByNameContaining(String name);

    public abstract void deleteAll();

    public abstract void deleteById(Integer id);
}
