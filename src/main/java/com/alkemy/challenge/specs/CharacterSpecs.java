package com.alkemy.challenge.specs;

import com.alkemy.challenge.model.Character;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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

/*
    public static Specification<Character>inMovie(Integer movieId){
        return (root, query, criteriaBuilder) ->
                movieId == null ? criteriaBuilder.conjunction()
                        :
                        criteriaBuilder.in();
    }
*/
}
