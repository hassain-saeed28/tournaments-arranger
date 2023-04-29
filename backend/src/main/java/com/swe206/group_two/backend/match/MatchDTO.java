package com.swe206.group_two.backend.match;

public class MatchDTO {
    private Integer firstParticipantScores;
    private Integer secondParticipantScores;

    public MatchDTO(
            Integer firstParticipantScores,
            Integer secondParticipantScores) {
        this.firstParticipantScores = firstParticipantScores;
        this.secondParticipantScores = secondParticipantScores;
    }

    public Integer getFirstParticipantScores() {
        return firstParticipantScores;
    }

    public Integer getSecondParticipantScores() {
        return secondParticipantScores;
    }

    public String toString() {
        return "Match{+"
                + "firstParticipantScores=" + firstParticipantScores + ", "
                + "secondParticipantScores=" + secondParticipantScores + ", "
                + "}";
    }
}
