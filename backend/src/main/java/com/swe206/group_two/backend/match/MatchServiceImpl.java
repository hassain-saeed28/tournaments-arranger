package com.swe206.group_two.backend.match;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe206.group_two.backend.participant.Participant;
import com.swe206.group_two.backend.participant.ParticipantServiceImpl;
import com.swe206.group_two.backend.rank.RankServiceImpl;
import com.swe206.group_two.backend.team.Team;
import com.swe206.group_two.backend.team.TeamServiceImpl;
import com.swe206.group_two.backend.tournament.Tournament;
import com.swe206.group_two.backend.tournament.TournamentServiceImpl;
import com.swe206.group_two.backend.utils.TournamentBased;
import com.swe206.group_two.backend.utils.TournamentType;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TournamentServiceImpl tournamentServiceImpl;

    @Autowired
    private ParticipantServiceImpl participantServiceImpl;

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private RankServiceImpl rankServiceImpl;

    @Override
    public List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    @Override
    public Optional<Match> getMatchById(Integer id) {
        return matchRepository.findById(id);
    }

    @Override
    public List<Match> getMatchesByTournamentId(Integer tournamentId) {
        return matchRepository.findAllByTournamentId(tournamentId);
    }

    @Override
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public void deleteAllMatches() {
        matchRepository.deleteAll();
    }

    @Override
    public void deleteMatchById(Integer id) {
        matchRepository.deleteById(id);
    }

    @Override
    public Match setMatchScoreById(Integer id,
            Integer firstParticipantScores, Integer secondParticipantScores) {
        Match match = matchRepository.findById(id).get();
        match.setFirstParticipantScores(firstParticipantScores);
        match.setSecondParticipantScores(secondParticipantScores);
        return matchRepository.save(match);
    }

    @Override
    public void setPoints(Integer id) {
        Match match = getMatchById(id).get();
        Tournament tournament = tournamentServiceImpl.getTournamentById(match.getTournamentId()).get();
        TournamentType type = tournament.getType();
        TournamentBased based = tournament.getBased();

        int score1 = match.getFirstParticipantScores();
        int score2 = match.getSecondParticipantScores();

        if (type.equals(TournamentType.RoundRobin)) {
            if (based.equals(TournamentBased.Individual)) {
                Participant participant1 = participantServiceImpl.getParticipantById(match.getFirstParticipantId())
                        .get();
                Participant participant2 = participantServiceImpl.getParticipantById(match.getSecondParticipantId())
                        .get();

                if (score1 > score2)
                    participant1.setParticipantCurrentPoints(participant1.getParticipantCurrentPoints() + 3);
                else if (score1 < score2)
                    participant2.setParticipantCurrentPoints(participant2.getParticipantCurrentPoints() + 3);
                else {
                    participant1.setParticipantCurrentPoints(participant1.getParticipantCurrentPoints() + 1);
                    participant2.setParticipantCurrentPoints(participant2.getParticipantCurrentPoints() + 1);
                }
            } else {

                Team team1 = teamServiceImpl.getTeamById(
                        participantServiceImpl.getParticipantById(match.getFirstParticipantId()).get().getTeamId())
                        .get();
                Team team2 = teamServiceImpl.getTeamById(
                        participantServiceImpl.getParticipantById(match.getSecondParticipantId()).get().getTeamId())
                        .get();

                List<Participant> teamOneParticipants = participantServiceImpl
                        .getAllParticipantsByTeamId(team1.getId());
                List<Participant> teamTwoParticipants = participantServiceImpl
                        .getAllParticipantsByTeamId(team2.getId());

                if (score1 > score2)
                    for (int i = 0; i < teamOneParticipants.size(); i++) {
                        Participant participant = teamOneParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 3);
                    }
                else if (score1 < score2)
                    for (int i = 0; i < teamTwoParticipants.size(); i++) {
                        Participant participant = teamTwoParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 3);
                    }
                else {
                    for (int i = 0; i < teamOneParticipants.size(); i++) {
                        Participant participant = teamOneParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 1);

                        participant = teamTwoParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 1);
                    }
                }
            }
        } else {
            if (based.equals(TournamentBased.Individual)) {

                Participant participant1 = participantServiceImpl.getParticipantById(match.getFirstParticipantId())
                        .get();
                Participant participant2 = participantServiceImpl.getParticipantById(match.getSecondParticipantId())
                        .get();

                if (score1 > score2)
                    participant2.setParticipantCurrentPoints(-1);
                else
                    participant1.setParticipantCurrentPoints(-1);

            } else {

                Team team1 = teamServiceImpl.getTeamById(
                        participantServiceImpl.getParticipantById(match.getFirstParticipantId()).get().getTeamId())
                        .get();
                Team team2 = teamServiceImpl.getTeamById(
                        participantServiceImpl.getParticipantById(match.getSecondParticipantId()).get().getTeamId())
                        .get();

                List<Participant> teamOneParticipants = participantServiceImpl
                        .getAllParticipantsByTeamId(team1.getId());
                List<Participant> teamTwoParticipants = participantServiceImpl
                        .getAllParticipantsByTeamId(team2.getId());

                if (score1 > score2)
                    for (int i = 0; i < teamOneParticipants.size(); i++) {
                        Participant participant = teamTwoParticipants.get(i);
                        participant.setParticipantCurrentPoints(-1);
                    }
                else
                    for (int i = 0; i < teamTwoParticipants.size(); i++) {
                        Participant participant = teamOneParticipants.get(i);
                        participant.setParticipantCurrentPoints(-1);
                    }

            }
        }
        calculateWinners(match.getTournamentId());
    }

    @Override
    public List<Match> generateMatches(Integer tournamentId) {
        List<Match> matches = new ArrayList<>();
        Tournament tournament = tournamentServiceImpl.getTournamentById(tournamentId).get();
        TournamentType type = tournament.getType();
        TournamentBased based = tournament.getBased();
        Integer daysBetweenStages = tournament.getDaysBetweenStages();
        LocalDate date = tournament.getStartDate();

        List<Participant> participants;
        List<Team> teams;
        Integer numberOfParticipants;

        if (type.equals(TournamentType.RoundRobin)) {
            if (based.equals(TournamentBased.Individual)) {
                participants = participantServiceImpl.getAllParticipantsByTournamentId(tournamentId).stream()
                        .filter(participant -> participant.getTeamId().equals(null)).collect(Collectors.toList());
                numberOfParticipants = participants.size();

                List<Integer> ids = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++) {
                    ids.add(participants.get(i).getId());
                }
                roundRobinSchedualing(ids, matches, tournamentId, daysBetweenStages, date);
            } else {
                teams = teamServiceImpl.getAllTeamsByTournamentId(tournamentId);
                numberOfParticipants = teams.size();

                List<Integer> ids = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++) {
                    ids.add(teams.get(i).getId());
                }
                roundRobinSchedualing(ids, matches, tournamentId, daysBetweenStages, date);
            }
        } else {
            if (based.equals(TournamentBased.Individual)) {
                participants = participantServiceImpl.getAllParticipantsByTournamentId(tournamentId).stream()
                        .filter(participant -> participant.getTeamId().equals(null)).collect(Collectors.toList());
                numberOfParticipants = participants.size();

                List<Integer> ids = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++) {
                    if (participants.get(i).getParticipantCurrentPoints() > 0)
                        ids.add(participants.get(i).getId());
                }
                eliminationSchedualing(ids, matches, tournamentId, daysBetweenStages, date);
            } else {
                teams = teamServiceImpl.getAllTeamsByTournamentId(tournamentId);
                numberOfParticipants = teams.size();

                List<Integer> ids = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++) {
                    if (participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId()).get(i)
                            .getParticipantCurrentPoints() > 0)
                        ids.add(teams.get(i).getId());
                }
                eliminationSchedualing(ids, matches, tournamentId, daysBetweenStages, date);
            }
        }
        return matches;
    }

    public void roundRobinSchedualing(List<Integer> usersIds, List<Match> matches, Integer tournamentId,
            Integer daysBetweenStages, LocalDate date) {

        int numberOfParticipants = usersIds.size();

        if (numberOfParticipants % 2 != 0) {
            usersIds.add(0); // If odd number of teams add a dummy
            numberOfParticipants++;
        }

        int daysToFinishTheTournament = (numberOfParticipants - 1); // rounds needed to complete tournament
        int halfSize = numberOfParticipants / 2;

        List<Integer> teams = new ArrayList<>();

        teams.addAll(usersIds); // Add teams to List and remove the first team
        teams.remove(0);

        int teamsLength = teams.size();
        int i = 0;
        for (int day = 0; day < daysToFinishTheTournament; day++) {

            int teamId = day % teamsLength;
            if (teams.get(teamId) == 0 || teams.get(0) == 0) {
            } else {
                matches.add(new Match(tournamentId, teams.get(teamId),
                        teams.get(0), null,
                        null, date.plusDays(i * daysBetweenStages)));
            }

            for (int id = 1; id < halfSize; id++) {
                int firstTeam = (day + id) % teamsLength;
                int secondTeam = (day + teamsLength - id) % teamsLength;
                if (teams.get(firstTeam) == 0 || teams.get(secondTeam) == 0) {
                } else {
                    matches.add(new Match(tournamentId, teams.get(firstTeam),
                            teams.get(secondTeam), null,
                            null, date.plusDays(i * daysBetweenStages)));
                }
            }
            i++;
        }
    }

    public void eliminationSchedualing(List<Integer> usersIds, List<Match> matches, Integer tournamentId,
            Integer daysBetweenStages, LocalDate date) {

        if (usersIds.size() == 1) {
            calculateWinners(tournamentId);
            return;
        }

        int i = 0;
        if (date.compareTo(LocalDate.now()) < 0) {
            date = LocalDate.now();
            i++;
        }

        while (i < usersIds.size() / 2 - 1) {
            matches.add(new Match(tournamentId, usersIds.get(2 * i), usersIds.get(2 * i + 1),
                    null, null, date.plusDays(i * daysBetweenStages)));
            i++;
        }
    }

    public void calculateWinners(Integer tournamentId) {
        Tournament tournament = tournamentServiceImpl.getTournamentById(tournamentId).get();
        TournamentType type = tournament.getType();
        TournamentBased based = tournament.getBased();

        List<Participant> participants;
        List<Team> teams;
        Integer numberOfParticipants;

        if (type.equals(TournamentType.RoundRobin)) {
            if (based.equals(TournamentBased.Individual)) {
                participants = participantServiceImpl.getAllParticipantsByTournamentId(tournamentId).stream()
                        .filter(participant -> participant.getTeamId().equals(null)).collect(Collectors.toList());
                numberOfParticipants = participants.size();

                Collections.sort(participants);

                // no draw case
                for (int i = 0; i < numberOfParticipants; i++)
                    rankServiceImpl.getRankByParticipantId(participants.get(i).getId()).setCurrentRank(i + 1);

                // draw case
                for (int i = 1; i < numberOfParticipants; i++) {
                    if (participants.get(i - 1).getParticipantCurrentPoints() == participants.get(i)
                            .getParticipantCurrentPoints()) {
                        draw(tournamentId, participants.get(i - 1), participants.get(i));
                    }
                }

            } else {
                teams = teamServiceImpl.getAllTeamsByTournamentId(tournamentId);
                numberOfParticipants = teams.size();

                participants = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++)
                    participants.add(participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId()).get(0));

                Collections.sort(participants);

                teams.clear();

                for (int i = 0; i < numberOfParticipants; i++)
                    teams.add(teamServiceImpl.getTeamById(participants.get(i).getTeamId()).get());

                // no draw case
                for (int i = 0; i < numberOfParticipants; i++) {
                    rankServiceImpl.getRankById(teams.get(i).getRankId()).get().setCurrentRank(i + 1);
                    participants = participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId());
                    for (int j = 0; j < participants.size(); j++) {
                        rankServiceImpl.getRankByParticipantId(participants.get(i).getId()).setCurrentRank(i + 1);
                    }
                }

                participants.clear();

                for (int i = 0; i < numberOfParticipants; i++)
                    participants.add(participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId()).get(0));

                Collections.sort(participants);

                // draw case
                for (int i = 1; i < numberOfParticipants; i++) {
                    if (participants.get(i - 1).getParticipantCurrentPoints() == participants.get(i)
                            .getParticipantCurrentPoints()) {
                        draw(tournamentId, participants.get(i - 1), participants.get(i));
                    }
                }
            }
        } else {
            if (based.equals(TournamentBased.Individual)) {
                participants = participantServiceImpl.getAllParticipantsByTournamentId(tournamentId).stream()
                        .filter(participant -> participant.getTeamId().equals(null)).collect(Collectors.toList());

                Collections.sort(participants);

                if (participants.get(1).getParticipantCurrentPoints() < 0)
                    rankServiceImpl.getRankByParticipantId(participants.get(0).getId()).setCurrentRank(1);

            } else {
                teams = teamServiceImpl.getAllTeamsByTournamentId(tournamentId);
                numberOfParticipants = teams.size();

                participants = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++)
                    participants.add(participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId()).get(0));

                Collections.sort(participants);

                int secondParticipantScore = participants.get(1).getParticipantCurrentPoints();
                if (secondParticipantScore < 0) {

                    Team team = teamServiceImpl.getTeamById(participants.get(0).getTeamId()).get();
                    rankServiceImpl.getRankById(team.getRankId()).get().setCurrentRank(1);
                    participants.clear();
                    participants = participantServiceImpl.getAllParticipantsByTeamId(team.getId());

                    for (int i = 0; i < participants.size(); i++)
                        rankServiceImpl.getRankByParticipantId(participants.get(i).getId()).setCurrentRank(1);
                }
            }
        }
    }

    private void draw(Integer tournamentId, Participant participant1, Participant participant2) {
        List<Match> matches = getMatchesByTournamentId(tournamentId);
        int wins1 = 0;
        int wins2 = 0;
        int goals1 = 0;
        int goals2 = 0;
        int goalsRecived1 = 0;
        int goalsRecived2 = 0;

        for (int i = 0; i < matches.size(); i++) {
            if ((participant1.getId() == matches.get(i).getFirstParticipantId()
                    && participant2.getId() == matches.get(i).getSecondParticipantId())
                    || (participant1.getId() == matches.get(i).getSecondParticipantId()
                            && participant2.getId() == matches.get(i).getFirstParticipantId())) {

                if (matches.get(i).getFirstParticipantScores() == matches.get(i).getSecondParticipantScores()) {
                    for (int j = 0; i < matches.size(); i++) {
                        if (participant1.getId() == matches.get(i).getFirstParticipantId()) {
                            if (matches.get(i).getFirstParticipantScores() > matches.get(i)
                                    .getSecondParticipantScores())
                                wins1++;
                            goals1 += matches.get(i).getFirstParticipantScores();
                            goalsRecived1 += matches.get(i).getSecondParticipantScores();

                        } else if (participant1.getId() == matches.get(i).getSecondParticipantId()) {
                            if (matches.get(i).getFirstParticipantScores() < matches.get(i)
                                    .getSecondParticipantScores())
                                wins1++;
                            goals1 += matches.get(i).getSecondParticipantScores();
                            goalsRecived1 += matches.get(i).getFirstParticipantScores();

                        } else if (participant2.getId() == matches.get(i).getFirstParticipantId()) {
                            if (matches.get(i).getFirstParticipantScores() > matches.get(i)
                                    .getSecondParticipantScores())
                                wins2++;
                            goals2 += matches.get(i).getFirstParticipantScores();
                            goalsRecived2 += matches.get(i).getSecondParticipantScores();

                        } else if (participant2.getId() == matches.get(i).getSecondParticipantId()) {
                            if (matches.get(i).getFirstParticipantScores() < matches.get(i)
                                    .getSecondParticipantScores())
                                wins2++;
                            goals2 += matches.get(i).getSecondParticipantScores();
                            goalsRecived2 += matches.get(i).getFirstParticipantScores();

                        }
                    }
                }
            }
        }
        Tournament tournament = tournamentServiceImpl.getTournamentById(tournamentId).get();
        TournamentBased based = tournament.getBased();

        List<Participant> participantsOfTeamOne = participantServiceImpl
                .getAllParticipantsByTeamId(participant1.getTeamId());
        List<Participant> participantsOfTeamTwo = participantServiceImpl
                .getAllParticipantsByTeamId(participant2.getTeamId());

        if (based.equals(TournamentBased.Individual)) {
            if (wins1 > wins2) {
                rankServiceImpl.getRankByParticipantId(participant2.getId())
                        .setCurrentRank(
                                rankServiceImpl.getRankByParticipantId(participant1.getId()).getCurrentRank() + 1);
            } else if (wins1 < wins2) {
                rankServiceImpl.getRankByParticipantId(participant1.getId())
                        .setCurrentRank(
                                rankServiceImpl.getRankByParticipantId(participant2.getId()).getCurrentRank() + 1);
            } else {
                if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    rankServiceImpl.getRankByParticipantId(participant2.getId())
                            .setCurrentRank(
                                    rankServiceImpl.getRankByParticipantId(participant1.getId()).getCurrentRank() + 1);
                } else if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    rankServiceImpl.getRankByParticipantId(participant1.getId())
                            .setCurrentRank(
                                    rankServiceImpl.getRankByParticipantId(participant2.getId()).getCurrentRank() + 1);
                }
            }
        } else {

            Team team1 = teamServiceImpl.getTeamById(participant1.getTeamId()).get();
            Team team2 = teamServiceImpl.getTeamById(participant2.getTeamId()).get();

            if (wins1 > wins2) {
                rankServiceImpl.getRankById(team2.getRankId()).get()
                        .setCurrentRank(rankServiceImpl.getRankById(team1.getRankId()).get().getCurrentRank() + 1);
            } else if (wins1 < wins2) {
                rankServiceImpl.getRankById(team1.getRankId()).get()
                        .setCurrentRank(rankServiceImpl.getRankById(team2.getRankId()).get().getCurrentRank() + 1);
            } else {
                if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    rankServiceImpl.getRankById(team2.getRankId()).get()
                            .setCurrentRank(rankServiceImpl.getRankById(team1.getRankId()).get().getCurrentRank() + 1);
                } else if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    rankServiceImpl.getRankById(team1.getRankId()).get()
                            .setCurrentRank(rankServiceImpl.getRankById(team2.getRankId()).get().getCurrentRank() + 1);
                }
            }

            for (int i = 0; i < participantsOfTeamOne.size(); i++) {
                if (wins1 > wins2) {
                    rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId())
                            .setCurrentRank(
                                    rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId())
                                            .getCurrentRank() + 1);
                } else if (wins1 < wins2) {
                    rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId())
                            .setCurrentRank(
                                    rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId())
                                            .getCurrentRank() + 1);
                } else {
                    if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                        rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId())
                                .setCurrentRank(
                                        rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId())
                                                .getCurrentRank()
                                                + 1);
                    } else if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                        rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId())
                                .setCurrentRank(
                                        rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId())
                                                .getCurrentRank()
                                                + 1);
                    }
                }
            }
        }
    }
}
