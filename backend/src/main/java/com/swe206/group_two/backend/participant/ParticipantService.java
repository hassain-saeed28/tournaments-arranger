package com.swe206.group_two.backend.participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    public abstract List<Participant> getAllParticipants();

    public abstract Optional<Participant> getParticipantById(Integer id);

    public abstract List<Participant> getAllParticipantsByTournamentId(Integer id);

    public abstract List<Participant> getAllParticipantsByTeamId(Integer id);

    public abstract Participant createParticipant(Participant participant);

    public abstract void deleteAllParticipants();

    public abstract void deleteParticipantById(Integer id);
}
