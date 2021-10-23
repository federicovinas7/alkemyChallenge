package com.alkemy.challenge.service;

import com.alkemy.challenge.model.DTO.MovieDTO;
import com.alkemy.challenge.model.Movie;
import com.alkemy.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Movie getById(Integer movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"movie with id " +movieId + "doesn't exists"));
    }

    public List<Movie>getByTitle(String movieTitle,String order) {

        Sort.Direction direction = Sort.Direction.ASC;
        if(order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

            return  movieRepository.getAllByTitleContaining(movieTitle,Sort.by(direction,"title"));
    }
}
