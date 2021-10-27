package com.alkemy.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

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
    private List<Movie>movieList;


}
