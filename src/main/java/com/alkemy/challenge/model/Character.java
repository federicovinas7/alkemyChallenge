package com.alkemy.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name="characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String image;
    private String name;
    private Integer age;
    private Float weight;
    private String story;
    @ManyToMany
    @JsonIgnore
    @JoinTable(name="characterPerMovie",joinColumns = @JoinColumn(name="characterId"),inverseJoinColumns = @JoinColumn(name="movieId"))
    private Set<Movie> movieList;


}
