package com.swe206.group_two.backend.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private RankRepository rankRepository;

    @Override
    public List<Rank> getAllRanks() {
        List<Rank> ranks = new ArrayList<>();
        rankRepository.findAll().forEach(ranks::add);
        return ranks;
    }

    @Override
    public Optional<Rank> getRankById(Integer id) {
        return rankRepository.findById(id);
    }

    @Override
    public List<Rank> getAllRanksByTournamentId(Integer id) {
        return rankRepository.findAllByTournamentId(id);
    }

    @Override
    public Rank getRankByParticipantId(Integer id) {
        return rankRepository.findByParticipantId(id);
    }

    @Override
    public Rank createRank(Rank rank) {
        return rankRepository.save(rank);
    }

    @Override
    public void deleteAllRanks() {
        rankRepository.deleteAll();
    }

    @Override
    public void deleteRankById(Integer id) {
        rankRepository.deleteById(id);
    }
}
