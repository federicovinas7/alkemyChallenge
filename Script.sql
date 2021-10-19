CREATE DATABASE disney;

USE disney;

CREATE TABLE characters (
                            id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            image VARCHAR(50),
                            NAME VARCHAR(50),
                            age INT,
                            weight FLOAT,
                            story VARCHAR(50)
)

CREATE TABLE characterPerMovie(
                                  characterId INT,
                                  movieId INT,
                                  CONSTRAINT pk_cpm PRIMARY KEY (characterId,movieId),
                                  CONSTRAINT fk_characters FOREIGN KEY (characterId) REFERENCES characters (id),
                                  CONSTRAINT fk_movies FOREIGN KEY (movieId) REFERENCES movies (id)
)

CREATE TABLE movies(
                       id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                       image VARCHAR(50),
                       title VARCHAR(50),
                       creationDate DATE,
                       qualification INT
)

CREATE TABLE genres(
                       id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                       NAME VARCHAR(50),
                       image VARCHAR(50)
);

CREATE TABLE genresPerMovie(
                               genreId INT NOT NULL,
                               movieId INT NOT NULL,
                               CONSTRAINT pk_gpm PRIMARY KEY (genreId,movieId),
                               CONSTRAINT fk_gpm_genres FOREIGN KEY (genreId) REFERENCES genres(id),
                               CONSTRAINT fk_gpm_movies FOREIGN KEY (movieId) REFERENCES movies(id)
);
