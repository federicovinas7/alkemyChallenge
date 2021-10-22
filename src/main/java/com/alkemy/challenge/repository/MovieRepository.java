package com.alkemy.challenge.repository;

import com.alkemy.challenge.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

   @Query(value = "SELECT * FROM movies  WHERE title LIKE %?1% ORDER BY title ?2",nativeQuery = true)
    List<Movie>searchByTitle(String title,String order);


}
