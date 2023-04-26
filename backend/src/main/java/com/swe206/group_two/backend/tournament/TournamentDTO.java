package com.swe206.group_two.backend.tournament;

import java.time.LocalDate;

import com.swe206.group_two.backend.sport.Sport;
import com.swe206.group_two.backend.utils.TournamentBased;
import com.swe206.group_two.backend.utils.TournamentType;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "tournaments")
public class TournamentDTO {
    @Column(name = "tournament_name", nullable = false)
    private String name;

    @Column(name = "tournament_type", nullable = false)
    private TournamentType type;

    @Column(name = "tournament_based", nullable = false)
    private TournamentBased based;

    @OneToOne
    @JoinColumn(name = "sport_id")
    private Integer sportId;

    @Column(name = "team_max_student", nullable = false)
    private int maxStudent;

    @Column(name = "days_between_stages", nullable = false)
    private int daysBetweenStages;

    @Column(name = "tournament_is_archived", nullable = false)
    private boolean archive = false;

    @Column(name = "tournament_is_open", nullable = false)
    private boolean open = true;

    @Column(name = "tournament_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "tournament_end_date", nullable = false)
    private LocalDate endDate;

    public TournamentDTO() {
    }

    public TournamentDTO(String name, TournamentType type,
            TournamentBased based, Integer sportId, int maxStudent,
            int daysBetweenStages, boolean archive, boolean open,
            LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.type = type;
        this.based = based;
        this.sportId = sportId;
        this.maxStudent = maxStudent;
        this.daysBetweenStages = daysBetweenStages;
        this.archive = archive;
        this.open = open;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TournamentType getType() {
        return type;
    }

    public void setType(TournamentType type) {
        this.type = type;
    }

    public TournamentBased getBased() {
        return based;
    }

    public void setBased(TournamentBased based) {
        this.based = based;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    public int getDaysBetweenStages() {
        return daysBetweenStages;
    }

    public void setDaysBetweenStages(int daysBetweenStages) {
        this.daysBetweenStages = daysBetweenStages;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Tournament toEntity(Sport sport) {
        return new Tournament(name, type, based, sport, maxStudent,
                daysBetweenStages, archive, open, startDate, endDate);
    }

    @Override
    public String toString() {
        return "TournamentDTO{"
                + "name='" + name + "', "
                + "type='" + type + "', "
                + "based='" + based + "', "
                + "sportId=" + sportId + ", "
                + "maxStudent=" + maxStudent + ", "
                + "daysBetweenStages=" + daysBetweenStages + ", "
                + "archive=" + archive + ", "
                + "open=" + open + ", "
                + "startDate='" + startDate + "', "
                + "endDate='" + endDate + "'"
                + "}";
    }
}
