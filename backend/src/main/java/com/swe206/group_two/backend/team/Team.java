package com.swe206.group_two.backend.team;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    private Integer id;

    @Column(name = "team_name", nullable = false)
    private String name;

    @Column(name = "tournament_id", nullable = false)
    private Integer tournamentId;

    @Column(name = "rank_id", nullable = false)
    private Integer rankId;

    public Team() {
    }

    public Team(String name, Integer tournamentId, Integer rankId) {
        this.name = name;
        this.tournamentId = tournamentId;
        this.rankId = rankId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public Integer getRankId() {
        return rankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Team))
            return false;
        Team team = (Team) o;
        return Objects.equals(this.id, team.id)
                && Objects.equals(this.name, team.name)
                && Objects.equals(this.tournamentId, team.tournamentId)
                && Objects.equals(this.rankId, team.rankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.name,
                this.tournamentId,
                this.rankId);
    }

    @Override
    public String toString() {
        return "Team{"
                + "id=" + this.id + ", "
                + "name='" + this.name + "', "
                + "tournamentId=" + this.tournamentId + ", "
                + "rankId=" + this.rankId + ""
                + '}';
    }
}
