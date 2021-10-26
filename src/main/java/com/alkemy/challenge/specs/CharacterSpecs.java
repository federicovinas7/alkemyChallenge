package com.alkemy.challenge.specs;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.Movie;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Collection;

@Component
public class CharacterSpecs {

    public static Specification<Character> ageEquals(Integer age){
        return (root, query, criteriaBuilder) ->
                age==null ?
                criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("age"),age);
    }

    public static Specification<Character>weightEquals(Float weight){
        return (root, query, criteriaBuilder) ->
                weight == null ? criteriaBuilder.conjunction()
                        :
                criteriaBuilder.equal(root.get("weight"),weight);
    }

    public static Specification<Character>hasNameLike(String name){
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction()
                        :
                        criteriaBuilder.like(root.get("name"),"%" + name +"%");

    }


    public static Specification<Character>inMovie(Integer movieId){

       return  movieId==null ?  ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction())
            :
         (root, query, criteriaBuilder) -> {

            query.distinct(true);
            Root<Character> character = root;
            Subquery<Movie> movieSubquery = query.subquery(Movie.class);
            Root<Movie>movie =movieSubquery.from(Movie.class);
            Expression<Collection<Character>>characterMovies = movie.get("characterList");
            movieSubquery.select(movie);
            movieSubquery.where(criteriaBuilder.equal(movie.get("id"),movieId),criteriaBuilder.isMember(character,characterMovies));
            return criteriaBuilder.exists(movieSubquery);
        };
    }

}
