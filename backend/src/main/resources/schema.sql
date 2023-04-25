CREATE TABLE IF NOT EXISTS matches (
    match_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tournament_id INT NOT NULL,
    first_participant_id INT NOT NULL,
    second_participant_id INT NOT NULL,
    first_participant_scores INT,
    second_participant_scores INT,
    match_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS participants (
    participant_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    tournament_id INT NOT NULL,
    team_id INT,
    participant_current_points INT
);

CREATE TABLE IF NOT EXISTS ranks (
    rank_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tournament_id INT NOT NULL,
    participant_id INT,
    current_rank INT
);

CREATE TABLE IF NOT EXISTS sports (
    sport_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    sport_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS teams (
    team_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tournament_id INT NOT NULL,
    rank_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS tournaments (
    tournament_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tournament_name VARCHAR NOT NULL,
    tournament_type VARCHAR CHECK (tournament_type IN ('Elimination', 'RoundRobin')) NOT NULL,
    tournament_based VARCHAR CHECK (tournament_based IN ('Team', 'Individual')) NOT NULL,
    sport_id INT NOT NULL,
    team_max_student INT NOT NULL,
    days_between_stages INT NOT NULL,
    tournament_is_archived BOOLEAN NOT NULL,
    tournament_is_open BOOLEAN NOT NULL,
    tournament_start_date DATE NOT NULL,
    tournament_end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_name VARCHAR NOT NULL,
    user_role VARCHAR CHECK (user_role IN ('Admin', 'Student')) NOT NULL,
    user_email VARCHAR NOT NULL,
    user_password_hash BINARY(128) NOT NULL
);

SELECT
    *
FROM
    matches
    INNER JOIN tournaments ON matches.tournament_id = tournaments.tournament_id
    INNER JOIN participants ON matches.first_participant_id = participants.participant_id
    AND matches.second_participant_id = participants.participant_id;

SELECT
    *
FROM
    participants
    INNER JOIN users ON participants.user_id = users.user_id
    INNER JOIN tournaments ON participants.tournament_id = tournaments.tournament_id
    INNER JOIN teams ON participants.team_id = teams.team_id;

SELECT
    *
FROM
    ranks
    INNER JOIN tournaments ON ranks.tournament_id = tournaments.tournament_id
    INNER JOIN participants ON ranks.participant_id = participants.participant_id;

SELECT
    *
FROM
    teams
    INNER JOIN tournaments ON teams.tournament_id = tournaments.tournament_id
    INNER JOIN ranks ON teams.rank_id = ranks.rank_id;

SELECT
    *
FROM
    tournaments
    INNER JOIN sports ON sports.sport_id = tournaments.sport_id;
