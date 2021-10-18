package com.alkemy.challenge.service;

import com.alkemy.challenge.model.Genre;
import com.alkemy.challenge.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
