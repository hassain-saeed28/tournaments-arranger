package com.swe206.group_two.backend.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    public abstract List<User> findAll();

    @Override
    public abstract Optional<User> findById(Integer id);

    public abstract Optional<User> findByEmail(String email);

    @Override
    public abstract <S extends User> S save(S user);

    @Override
    public abstract void deleteAll();

    @Override
    public abstract void deleteById(Integer id);
}
