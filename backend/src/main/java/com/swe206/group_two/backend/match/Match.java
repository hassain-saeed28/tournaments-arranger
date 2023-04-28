package com.swe206.group_two.backend.match;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id", nullable = false)
    private Integer id;

    @Column(name = "tournament_id", nullable = false)
    private Integer tournamentId;

    @Column(name = "first_participant_id", nullable = false)
    private Integer firstParticipantId;

    @Column(name = "second_participant_id", nullable = false)
    private Integer secondParticipantId;

    @Column(name = "first_participant_scores")
    private Integer firstParticipantScores;

    @Column(name = "second_participant_scores")
    private Integer secondParticipantScores;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    public Match() {
    }

    public Match(Integer tournamentId, Integer firstParticipantId,
            Integer secondParticipantId, Integer firstParticipantScores,
            Integer secondParticipantScores, LocalDate matchDate) {
        this.tournamentId = tournamentId;
        this.firstParticipantId = firstParticipantId;
        this.secondParticipantId = secondParticipantId;
        this.firstParticipantScores = firstParticipantScores;
        this.secondParticipantScores = secondParticipantScores;
        this.matchDate = matchDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public Integer getFirstParticipantId() {
        return firstParticipantId;
    }

    public Integer getSecondParticipantId() {
        return secondParticipantId;
    }

    public Integer getFirstParticipantScores() {
        return firstParticipantScores;
    }

    public void setFirstParticipantScores(Integer firstParticipantScores) {
        this.firstParticipantScores = firstParticipantScores;
    }

    public Integer getSecondParticipantScores() {
        return secondParticipantScores;
    }

    public void setSecondParticipantScores(Integer secondParticipantScores) {
        this.secondParticipantScores = secondParticipantScores;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Match))
            return false;
        Match match = (Match) o;
        return Objects.equals(this.id, match.id)
                && Objects.equals(this.tournamentId, match.tournamentId)
                && Objects.equals(this.firstParticipantId, match.firstParticipantId)
                && Objects.equals(this.secondParticipantId, match.secondParticipantId)
                && Objects.equals(this.firstParticipantScores, match.firstParticipantScores)
                && Objects.equals(this.secondParticipantScores, match.secondParticipantScores)
                && Objects.equals(this.matchDate, match.matchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.tournamentId,
                this.firstParticipantId,
                this.secondParticipantId,
                this.firstParticipantScores,
                this.secondParticipantScores,
                this.matchDate);
    }

    @Override
    public String toString() {
        return "Match{"
                + "id=" + this.id + ", "
                + "tournamentId=" + this.tournamentId + ", "
                + "firstParticipantId=" + this.firstParticipantId + ", "
                + "secondParticipantId=" + this.secondParticipantId + ", "
                + "firstParticipantScores=" + this.firstParticipantScores + ", "
                + "secondParticipantScores=" + this.secondParticipantScores + ", "
                + "matchDate='" + this.matchDate + "'"
                + '}';
    }
}
