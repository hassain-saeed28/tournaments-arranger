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
import com.swe206.group_two.backend.participant.ParticipantRepository;
import com.swe206.group_two.backend.participant.ParticipantServiceImpl;
import com.swe206.group_two.backend.rank.Rank;
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
    private ParticipantRepository participantRepository;

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
        matchRepository.save(match);
        setPoints(id);
        calculateWinners(getMatchById(id).get().getTournamentId());
        return matchRepository.save(match);
    }

    @Override
    public void setPoints(Integer matchId) {
        Match match = getMatchById(matchId).get();
        Tournament tournament = tournamentServiceImpl.getTournamentById(match.getTournamentId()).get();
        TournamentType type = tournament.getType();
        TournamentBased based = tournament.getBased();

        Integer score1 = match.getFirstParticipantScores();
        Integer score2 = match.getSecondParticipantScores();

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

                participantRepository.save(participant1);
                participantRepository.save(participant2);

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
                        participantRepository.save(participant);
                    }
                else if (score1 < score2)
                    for (int i = 0; i < teamTwoParticipants.size(); i++) {
                        Participant participant = teamTwoParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 3);
                        participantRepository.save(participant);
                    }
                else {
                    for (int i = 0; i < teamOneParticipants.size(); i++) {
                        Participant participant = teamOneParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 1);
                        participantRepository.save(participant);

                        participant = teamTwoParticipants.get(i);
                        participant.setParticipantCurrentPoints(participant.getParticipantCurrentPoints() + 1);
                        participantRepository.save(participant);
                    }
                }
            }
        } else {

            isLastMatchOfStage(matchId);

            if (based.equals(TournamentBased.Individual)) {

                Participant participant1 = participantServiceImpl.getParticipantById(match.getFirstParticipantId())
                        .get();
                Participant participant2 = participantServiceImpl.getParticipantById(match.getSecondParticipantId())
                        .get();

                if (score1 > score2) {
                    participant2.setParticipantCurrentPoints(-1);
                    participantRepository.save(participant2);

                } else {

                    participant1.setParticipantCurrentPoints(-1);
                    participantRepository.save(participant1);
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
                        Participant participant = teamTwoParticipants.get(i);
                        participant.setParticipantCurrentPoints(-1);
                        participantRepository.save(participant);
                    }
                else
                    for (int i = 0; i < teamTwoParticipants.size(); i++) {

                        Participant participant = teamOneParticipants.get(i);
                        participant.setParticipantCurrentPoints(-1);
                        participantRepository.save(participant);
                    }
            }
        }
        calculateWinners(match.getTournamentId());
    }

    @Override
    public void generateMatches(Integer tournamentId) {
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
                        .filter(participant -> participant.getTeamId() == null).collect(Collectors.toList());
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
                        .filter(participant -> participant.getTeamId() == null).collect(Collectors.toList());
                numberOfParticipants = participants.size();

                List<Integer> ids = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++) {
                    if (participants.get(i).getParticipantCurrentPoints() == null)
                        ids.add(participants.get(i).getId());
                }
                eliminationSchedualing(ids, matches, tournamentId, daysBetweenStages, date);
            } else {
                teams = teamServiceImpl.getAllTeamsByTournamentId(tournamentId);
                numberOfParticipants = teams.size();

                List<Integer> ids = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++) {
                    if (participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId()).get(0)
                            .getParticipantCurrentPoints() == null) {
                        ids.add(teams.get(i).getId());
                    }
                }
                eliminationSchedualing(ids, matches, tournamentId, daysBetweenStages, date);
            }
        }
        for (int i = 0; i < matches.size(); i++) {
            createMatch(matches.get(i));
        }
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

        while (i < usersIds.size() / 2) {
            matches.add(new Match(tournamentId,
                    participantServiceImpl.getAllParticipantsByTeamId(usersIds.get(2 * i)).get(0).getId(),
                    participantServiceImpl.getAllParticipantsByTeamId(usersIds.get(2 * i + 1)).get(0).getId(),
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
                        .filter(participant -> participant.getTeamId() == null).collect(Collectors.toList());
                numberOfParticipants = participants.size();

                Collections.sort(participants);

                // no draw case
                for (int i = 0; i < numberOfParticipants; i++) {
                    Rank rank = rankServiceImpl.getRankByParticipantId(participants.get(i).getId());
                    rank.setCurrentRank(i + 1);
                    rankServiceImpl.createRank(rank);

                }

                // draw case
                for (int i = 1; i < numberOfParticipants; i++) {
                    if (participants.get(i - 1).getParticipantCurrentPoints() == participants.get(i)
                            .getParticipantCurrentPoints()) {
                        draw(tournamentId, participants.get(i - 1), participants.get(i), i + 1);
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
                        Rank rank = rankServiceImpl.getRankByParticipantId(participants.get(i).getId());
                        rank.setCurrentRank(i + 1);
                        rankServiceImpl.createRank(rank);

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
                        draw(tournamentId, participants.get(i - 1), participants.get(i), i + 1);
                    }
                }
            }
        } else {
            if (based.equals(TournamentBased.Individual)) {
                participants = participantServiceImpl.getAllParticipantsByTournamentId(tournamentId).stream()
                        .filter(participant -> participant.getTeamId() == null).collect(Collectors.toList());

                Collections.sort(participants);

                if (participants.get(0).getParticipantCurrentPoints() == null) {
                    Rank rank = rankServiceImpl.getRankByParticipantId(participants.get(0).getId());
                    rank.setCurrentRank(1);
                    rankServiceImpl.createRank(rank);
                }

            } else {
                teams = teamServiceImpl.getAllTeamsByTournamentId(tournamentId);
                numberOfParticipants = teams.size();

                participants = new ArrayList<>();

                for (int i = 0; i < numberOfParticipants; i++)
                    participants.add(participantServiceImpl.getAllParticipantsByTeamId(teams.get(i).getId()).get(0));

                Collections.sort(participants);

                Integer secondParticipantScore = participants.get(0).getParticipantCurrentPoints();
                if (secondParticipantScore == null) {

                    Team team = teamServiceImpl.getTeamById(participants.get(0).getTeamId()).get();
                    if (participants.get(0).getParticipantCurrentPoints() == null) {
                        Rank rank = rankServiceImpl.getRankByParticipantId(participants.get(0).getId());
                        rank.setCurrentRank(1);
                        rankServiceImpl.createRank(rank);
                    }
                    participants.clear();
                    participants = participantServiceImpl.getAllParticipantsByTeamId(team.getId());

                    for (int i = 0; i < participants.size(); i++) {
                        Rank rank = rankServiceImpl.getRankByParticipantId(participants.get(i).getId());
                        rank.setCurrentRank(1);
                        rankServiceImpl.createRank(rank);
                    }

                    Rank rank = rankServiceImpl
                            .getRankById(teamServiceImpl.getTeamById(
                                    participants.get(0).getTeamId()).get().getRankId())
                            .get();
                    rank.setCurrentRank(1);
                    rankServiceImpl.createRank(rank);
                }
            }
        }
    }

    private void draw(Integer tournamentId, Participant participant1, Participant participant2, int rank) {
        if (participant1.getParticipantCurrentPoints() == null ||
                participant2.getParticipantCurrentPoints() == null)
            return;

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
                            if (matches.get(i).getFirstParticipantScores() == null
                                    || matches.get(i).getSecondParticipantScores() == null)
                                return;

                            else if (matches.get(i).getFirstParticipantScores() > matches.get(i)
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
                Rank rank1 = rankServiceImpl.getRankByParticipantId(participant1.getId());
                rank1.setCurrentRank(rank);
                rankServiceImpl.createRank(rank1);

                Rank rank2 = rankServiceImpl.getRankByParticipantId(participant2.getId());
                rank2.setCurrentRank(rank + 1);
                rankServiceImpl.createRank(rank2);

            } else if (wins1 < wins2) {
                Rank rank1 = rankServiceImpl.getRankByParticipantId(participant2.getId());
                rank1.setCurrentRank(rank);
                rankServiceImpl.createRank(rank1);

                Rank rank2 = rankServiceImpl.getRankByParticipantId(participant1.getId());
                rank2.setCurrentRank(rank + 1);
                rankServiceImpl.createRank(rank2);

            } else {
                if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    Rank rank1 = rankServiceImpl.getRankByParticipantId(participant1.getId());
                    rank1.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank1);

                    Rank rank2 = rankServiceImpl.getRankByParticipantId(participant2.getId());
                    rank2.setCurrentRank(rank + 1);
                    rankServiceImpl.createRank(rank2);

                } else if (goals1 - goalsRecived1 < goals2 - goalsRecived2) {
                    Rank rank1 = rankServiceImpl.getRankByParticipantId(participant2.getId());
                    rank1.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank1);

                    Rank rank2 = rankServiceImpl.getRankByParticipantId(participant1.getId());
                    rank2.setCurrentRank(rank + 1);
                    rankServiceImpl.createRank(rank2);
                }
            }
        } else {

            Team team1 = teamServiceImpl.getTeamById(participant1.getTeamId()).get();
            Team team2 = teamServiceImpl.getTeamById(participant2.getTeamId()).get();

            if (wins1 > wins2) {
                Rank rank1 = rankServiceImpl.getRankById(team1.getRankId()).get();
                rank1.setCurrentRank(rank);
                rankServiceImpl.createRank(rank1);

                Rank rank2 = rankServiceImpl.getRankById(team2.getRankId()).get();
                rank2.setCurrentRank(rank + 1);
                rankServiceImpl.createRank(rank2);

            } else if (wins1 < wins2) {
                Rank rank1 = rankServiceImpl.getRankById(team2.getRankId()).get();
                rank1.setCurrentRank(rank);
                rankServiceImpl.createRank(rank1);

                Rank rank2 = rankServiceImpl.getRankById(team1.getRankId()).get();
                rank2.setCurrentRank(rank + 1);
                rankServiceImpl.createRank(rank2);

            } else {
                if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    Rank rank1 = rankServiceImpl.getRankById(team1.getRankId()).get();
                    rank1.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank1);

                    Rank rank2 = rankServiceImpl.getRankById(team2.getRankId()).get();
                    rank2.setCurrentRank(rank + 1);
                    rankServiceImpl.createRank(rank2);

                } else if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                    Rank rank1 = rankServiceImpl.getRankById(team2.getRankId()).get();
                    rank1.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank1);

                    Rank rank2 = rankServiceImpl.getRankById(team1.getRankId()).get();
                    rank2.setCurrentRank(rank + 1);
                    rankServiceImpl.createRank(rank2);
                }
            }

            for (int i = 0; i < participantsOfTeamOne.size(); i++) {
                if (wins1 > wins2) {
                    Rank rank1 = rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId());
                    rank1.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank1);

                    Rank rank2 = rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId());
                    rank2.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank2);
                } else if (wins1 < wins2) {
                    Rank rank1 = rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId());
                    rank1.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank1);

                    Rank rank2 = rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId());
                    rank2.setCurrentRank(rank);
                    rankServiceImpl.createRank(rank2);
                } else {
                    if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                        Rank rank1 = rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId());
                        rank1.setCurrentRank(rank);
                        rankServiceImpl.createRank(rank1);

                        Rank rank2 = rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId());
                        rank2.setCurrentRank(rank);
                        rankServiceImpl.createRank(rank2);
                    } else if (goals1 - goalsRecived1 > goals2 - goalsRecived2) {
                        Rank rank1 = rankServiceImpl.getRankByParticipantId(participantsOfTeamTwo.get(i).getId());
                        rank1.setCurrentRank(rank);
                        rankServiceImpl.createRank(rank1);

                        Rank rank2 = rankServiceImpl.getRankByParticipantId(participantsOfTeamOne.get(i).getId());
                        rank2.setCurrentRank(rank);
                        rankServiceImpl.createRank(rank2);
                    }
                }
            }
        }
    }

    private void isLastMatchOfStage(Integer matchId) {
        List<Match> matches = getMatchesByTournamentId(getMatchById(matchId).get().getTournamentId());
        if (tournamentServiceImpl.getTournamentById(matches.get(0).getTournamentId()).get().getBased()
                .equals(TournamentBased.Individual)) {
            List<Participant> participants = participantServiceImpl
                    .getAllParticipantsByTournamentId(matches.get(0).getTournamentId()).stream()
                    .filter(participant -> participant.getTeamId() == null).collect(Collectors.toList());
            Match lastMatch = matches.get(matches.size() - 1);

            if (matches.size() != participants.size() - 1 && matchId == lastMatch.getId()) {
                generateMatches(matches.get(0).getTournamentId());
            }
        } else {
            List<Team> teams = teamServiceImpl.getAllTeamsByTournamentId(matches.get(0).getTournamentId());
            Match lastMatch = matches.get(matches.size() - 1);

            if (matches.size() != teams.size() - 1 && matchId == lastMatch.getId()) {
                generateMatches(matches.get(0).getTournamentId());
            }
        }
    }
}
