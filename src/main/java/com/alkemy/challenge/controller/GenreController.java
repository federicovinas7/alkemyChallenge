package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.Genre;
import com.alkemy.challenge.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alkemy.challenge.filter.JWTAuthorizationFilter.verifyAuthentication;
import static com.alkemy.challenge.utils.EntityURIBuilder.buildURI;

@RestController
@RequestMapping("/genres")
public class GenreController {

    GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<Genre> addGenre(Authentication auth,@RequestBody Genre genre){
        verifyAuthentication(auth);
        Genre g = genreService.addGenre(genre);
        return ResponseEntity.created(buildURI(g.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll(Authentication auth){

        verifyAuthentication(auth);
        List<Genre>genres = genreService.getAll();
        return ResponseEntity.status(genres.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(genres);
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteGenre(Authentication auth,@PathVariable Integer genreId){
        verifyAuthentication(auth);
        genreService.deleteGenre(genreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Genre>updateGenre(Authentication auth,@RequestBody Genre genre){
        Genre updatedGenre = genreService.updateGenre(genre);
        return new ResponseEntity<>(updatedGenre,HttpStatus.OK);
    }
}
