package com.swe206.group_two.backend.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe206.group_two.backend.sport.SportRepository;

@Service
public class TournamentServiceImpl implements TournamentService {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private SportRepository sportRepository;

    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<Tournament>();
        tournamentRepository.findAll().forEach(tournaments::add);
        return tournaments;
    }

    @Override
    public Optional<Tournament> getTournamentById(Integer id) {
        return tournamentRepository.findById(id);
    }

    @Override
    public Tournament createTournament(TournamentDTO tournamentDTO) {
        return tournamentRepository.save(tournamentDTO.toEntity(
                sportRepository.findById(tournamentDTO.getSportId()).get()));
    }

    @Override
    public Tournament updateTournamentById(Integer id, TournamentDTO tournamentDTO) {
        Tournament _tournament = tournamentRepository.findById(id).get();
        _tournament.setArchive(tournamentDTO.isArchive());
        _tournament.setOpen(tournamentDTO.isOpen());
        return tournamentRepository.save(_tournament);
    }

    @Override
    public void deleteAllTournament() {
        tournamentRepository.deleteAll();
    }

    @Override
    public void deleteTournamentById(Integer id) {
        tournamentRepository.deleteById(id);
    }
}
