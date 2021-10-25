package com.alkemy.challenge.specs;

import com.alkemy.challenge.model.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecs {

    public static Specification<Movie>hasTitleLike(String title){
        return (root, query, criteriaBuilder) ->
                title == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("title"),"%" + title + "%");

    }

    public static Specification<Movie>hasGenre(Integer genreId){

        return (root, query, criteriaBuilder) ->
                genreId == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("genre").get("id"),genreId);
    }


}
