package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.DTO.MovieDTO;
import com.alkemy.challenge.model.Movie;
import com.alkemy.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alkemy.challenge.utils.EntityURIBuilder.buildURI;

@RestController
@RequestMapping("/movies")
public class MovieController {

    MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAll(){
        List<MovieDTO>movies = movieService.getAll();
        return ResponseEntity.status(movies.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(movies);
    }
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie m =  movieService.addMovie(movie);
        return ResponseEntity.created(buildURI(m.getId())).build();
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable Integer movieId){
        movieService.deleteMovie(movieId);
    }
}
