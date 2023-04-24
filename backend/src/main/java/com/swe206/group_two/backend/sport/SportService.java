package com.swe206.group_two.backend.sport;

import java.util.List;
import java.util.Optional;

public interface SportService {
    public abstract List<Sport> getAllSports();

    public abstract Optional<Sport> getSportById(Integer id);

    public abstract List<Sport> getSportByNameContaining(String name);

    public abstract Sport createSport(Sport sport);

    public abstract Sport updateSportById(Integer id, Sport sport);

    public abstract void deleteAllSports();

    public abstract void deleteSportById(Integer id);
}
