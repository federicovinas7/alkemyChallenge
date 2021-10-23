package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.DTO.MovieDTO;
import com.alkemy.challenge.model.Movie;
import com.alkemy.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alkemy.challenge.filter.JWTAuthorizationFilter.verifyAuthentication;
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
    public ResponseEntity<List<MovieDTO>> getAll(Authentication auth){
        verifyAuthentication(auth);
        List<MovieDTO>movies = movieService.getAll();
        return ResponseEntity.status(movies.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie>getById(Authentication auth,@PathVariable Integer movieId){
        verifyAuthentication(auth);
        return ResponseEntity.ok(movieService.getById(movieId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>>searchByTitle(Authentication auth,@RequestParam String movieTitle,@RequestParam (defaultValue = "ASC")String order){
        verifyAuthentication(auth);
        List<Movie>movies = movieService.getByTitle(movieTitle,order);
      return ResponseEntity.status(movies.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(movies);
    }
    @PostMapping
    public ResponseEntity<Movie> addMovie(Authentication auth,@RequestBody Movie movie){
        verifyAuthentication(auth);
        Movie m =  movieService.addMovie(movie);
        return ResponseEntity.created(buildURI(m.getId())).build();
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(Authentication auth,@PathVariable Integer movieId){
        verifyAuthentication(auth);
        movieService.deleteMovie(movieId);
    }


}
