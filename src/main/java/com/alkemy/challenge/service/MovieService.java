package com.alkemy.challenge.service;

import com.alkemy.challenge.model.DTO.MovieDTO;
import com.alkemy.challenge.model.Movie;
import com.alkemy.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO>getAll(){
        List<Movie>movies = movieRepository.findAll();
        List<MovieDTO>movieDTOList = new ArrayList<>();
        for(Movie m : movies){
            movieDTOList.add(MovieDTO.builder()
                        .image(m.getImage())
                        .title(m.getTitle())
                        .creationDate(m.getCreationDate())
                        .build());
        }
        return movieDTOList;
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Integer movieId){

        movieRepository.deleteById(movieId);
    }
}
