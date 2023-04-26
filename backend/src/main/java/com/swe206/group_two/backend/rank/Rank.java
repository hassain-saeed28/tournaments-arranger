package com.swe206.group_two.backend.rank;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ranks")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id", nullable = false)
    private Integer id;

    @Column(name = "tournament_id", nullable = false)
    private Integer tournamentId;

    @Column(name = "participant_id")
    private Integer participantId;

    @Column(name = "current_rank")
    private Integer currentRank;

    public Rank() {
    }

    public Rank(Integer tournamentId, Integer participantId,
            Integer currentRank) {
        this.tournamentId = tournamentId;
        this.participantId = participantId;
        this.currentRank = currentRank;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public Integer getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(Integer currentRank) {
        this.currentRank = currentRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Rank))
            return false;
        Rank rank = (Rank) o;
        return Objects.equals(this.id, rank.id)
                && Objects.equals(this.tournamentId, rank.tournamentId)
                && Objects.equals(this.participantId, rank.participantId)
                && Objects.equals(this.currentRank, rank.currentRank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.tournamentId,
                this.participantId,
                this.currentRank);
    }

    @Override
    public String toString() {
        return "Rank{"
                + "id=" + this.id + ", "
                + "tournamentId=" + this.tournamentId + ", "
                + "participantId=" + this.participantId + ", "
                + "currentRank=" + this.currentRank + ""
                + '}';
    }
}
