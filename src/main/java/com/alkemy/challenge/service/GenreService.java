package com.alkemy.challenge.service;

import com.alkemy.challenge.model.Genre;
import com.alkemy.challenge.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.alkemy.challenge.utils.Constants.GENRE_NOT_FOUND;

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

    public void deleteGenre(Integer genreId) {
        if(genreRepository.existsById(genreId))
            genreRepository.deleteById(genreId);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format(GENRE_NOT_FOUND,genreId));
    }

    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
