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
    participant_id INT NOT NULL,
    current_rank INT NOT NULL
);

CREATE TABLE IF NOT EXISTS sports (
    sport_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    sport_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS teams (
    team_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tournament_id INT NOT NULL,
    rank_id INT
);

CREATE TABLE IF NOT EXISTS tournaments (
    tournament_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tournament_name VARCHAR NOT NULL,
    tournament_type VARCHAR(11) CHECK (tournament_type IN ('Elimination', 'RoundRobin')) NOT NULL,
    tournament_based VARCHAR(10) CHECK (tournament_based IN ('Team', 'Individual')) NOT NULL,
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
    user_role VARCHAR(7) CHECK (user_role IN ('Admin', 'Student')) NOT NULL,
    user_email VARCHAR NOT NULL,
    user_password_hash BINARY(128) NOT NULL
);
