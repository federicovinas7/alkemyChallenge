package com.alkemy.challenge.service;

import com.alkemy.challenge.model.DTO.MovieDTO;
import com.alkemy.challenge.model.Movie;
import com.alkemy.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.challenge.specs.MovieSpecs.hasGenre;
import static com.alkemy.challenge.specs.MovieSpecs.hasTitleLike;

@Service
public class MovieService {

    MovieRepository movieRepository;
    CharacterService characterService;

    @Autowired
    public MovieService(MovieRepository movieRepository, CharacterService characterService) {
        this.movieRepository = movieRepository;
        this.characterService = characterService;
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

    public List<Movie>getByTitle(String movieTitle,Integer genreId,String order) {

        Specification<Movie>spec1 = hasTitleLike(movieTitle);
        Specification<Movie>spec2 = hasGenre(genreId);
        Specification<Movie>specs = Specification.where(spec1).and(spec2);

        Sort.Direction direction = Sort.Direction.ASC;
        if(order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        return movieRepository.findAll(specs,Sort.by(direction,"creationDate"));
    }
}
