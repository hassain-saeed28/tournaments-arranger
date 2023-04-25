package com.swe206.group_two.backend.tournament;

import java.time.LocalDate;
import java.util.Objects;

import com.swe206.group_two.backend.sport.Sport;
import com.swe206.group_two.backend.utils.TournamentBased;
import com.swe206.group_two.backend.utils.TournamentType;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id", nullable = false)
    private Integer id;

    @Column(name = "tournament_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_type", nullable = false)
    private TournamentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_based", nullable = false)
    private TournamentBased based;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "sport_id")
    private Sport sport;

    @Column(name = "team_max_student", nullable = false)
    private int maxStudent;

    @Column(name = "days_between_stages", nullable = false)
    private int daysBetweenStages;

    @Column(name = "tournament_is_archived", nullable = false)
    private boolean archived;

    @Column(name = "tournament_is_open", nullable = false)
    private boolean open;

    @Column(name = "tournament_start_date", columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "tournament_end_date", columnDefinition = "DATE", nullable = false)
    private LocalDate endDate;

    public Tournament() {
    }

    public Tournament(String name, TournamentType type, TournamentBased based,
            Sport sport, int maxStudent, int daysBetweenStages,
            boolean isArchived, boolean isOpen, LocalDate startDate,
            LocalDate endDate) {
        this.name = name;
        this.type = type;
        this.based = based;
        this.sport = sport;
        this.maxStudent = maxStudent;
        this.daysBetweenStages = daysBetweenStages;
        this.archived = isArchived;
        this.open = isOpen;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public TournamentType getType() {
        return type;
    }

    public TournamentBased getBased() {
        return based;
    }

    public Sport getSport() {
        return this.sport;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public int getDaysBetweenStages() {
        return daysBetweenStages;
    }

    public boolean isArchive() {
        return archived;
    }

    public void setArchive(boolean archived) {
        this.archived = archived;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean isOpen) {
        this.open = isOpen;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Tournament))
            return false;
        Tournament tournament = (Tournament) o;
        return Objects.equals(this.id, tournament.id)
                && Objects.equals(this.name, tournament.name)
                && Objects.equals(this.type, tournament.type)
                && Objects.equals(this.based, tournament.based)
                && Objects.equals(this.sport, tournament.sport)
                && Objects.equals(this.maxStudent, tournament.maxStudent)
                && Objects.equals(this.daysBetweenStages, tournament.daysBetweenStages)
                && Objects.equals(this.archived, tournament.archived)
                && Objects.equals(this.open, tournament.open)
                && Objects.equals(this.startDate, tournament.startDate)
                && Objects.equals(this.endDate, tournament.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.name,
                this.based,
                this.sport,
                this.type,
                this.maxStudent,
                this.daysBetweenStages,
                this.archived,
                this.open,
                this.startDate,
                this.endDate);
    }

    @Override
    public String toString() {
        return "Tournament{"
                + "id=" + this.id + ", "
                + "name='" + this.name + "', "
                + "type='" + this.type + "', "
                + "based='" + this.based + "', "
                + "sport='" + this.sport + "'', "
                + "maxStudent=" + this.maxStudent + ", "
                + "daysBetweenStages=" + this.daysBetweenStages + ", "
                + "isArchived=" + this.archived + ", "
                + "isOpen=" + this.open + ", "
                + "startDate='" + this.startDate + "', "
                + "endDate='" + this.endDate + "'"
                + '}';
    }
}
