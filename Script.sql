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
                                  CONSTRAINT pk_cpm UNIQUE PRIMARY KEY (characterId,movieId),
                                  CONSTRAINT fk_characters FOREIGN KEY (characterId) REFERENCES characters (id),
                                  CONSTRAINT fk_movies FOREIGN KEY (movieId) REFERENCES movies (id)
)

CREATE TABLE movies(
                       id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                       image VARCHAR(50),
                       title VARCHAR(50),
                       creationDate DATE,
                       qualification INT,
                       genreId INT,
                       CONSTRAINT FK_movies_genres FOREIGN KEY (genreId) REFERENCES GENRES (id)
)

CREATE TABLE genres(
                       id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                       NAME VARCHAR(50),
                       image VARCHAR(50)
);


CREATE TABLE users(
                      id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      NAME VARCHAR(50),
                      lastname VARCHAR(50),
                      email VARCHAR(50) UNIQUE,
                      username VARCHAR(50) UNIQUE,
                      PASSWORD VARCHAR(50),
                      role VARCHAR(50)
);