package com.swe206.group_two.backend.participant;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "tournament_id", nullable = false)
    private Integer tournamentId;

    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "participant_current_points")
    private Integer participantCurrentPoints;

    public Participant() {
    }

    public Participant(Integer userId, Integer tournamentId, Integer teamId,
            Integer participantCurrentPoints) {
        this.userId = userId;
        this.tournamentId = tournamentId;
        this.participantCurrentPoints = participantCurrentPoints;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getParticipantCurrentPoints() {
        return participantCurrentPoints;
    }

    public void setParticipantCurrentPoints(
            Integer participantCurrentPoints) {
        this.participantCurrentPoints = participantCurrentPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Participant))
            return false;
        Participant participant = (Participant) o;
        return Objects.equals(this.id, participant.id)
                && Objects.equals(this.userId, participant.userId)
                && Objects.equals(this.tournamentId, participant.tournamentId)
                && Objects.equals(this.teamId, participant.teamId)
                && Objects.equals(this.participantCurrentPoints, participant.participantCurrentPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.userId,
                this.tournamentId,
                this.teamId,
                this.participantCurrentPoints);
    }

    @Override
    public String toString() {
        return "Participant{"
                + "id=" + this.id + ", "
                + "userId=" + this.userId + ", "
                + "tournamentId=" + this.tournamentId + ", "
                + "teamId=" + this.teamId + ", "
                + "participantCurrentPoints=" + this.participantCurrentPoints + ""
                + '}';
    }
}
