package com.alkemy.challenge.repository;

import com.alkemy.challenge.model.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

   List<Movie>getAllByTitleContaining(String title, Sort sort);




}
