package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.Genre;
import com.alkemy.challenge.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> addGenre(@RequestBody Genre genre){
        Genre g = genreService.addGenre(genre);
        return ResponseEntity.created(buildURI(g.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll(){

        List<Genre>genres = genreService.getAll();
        return ResponseEntity.status(genres.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(genres);

    }
}
