package com.alkemy.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String image;
    private Date creationDate;
    private Integer qualification;
    @ManyToMany(mappedBy = "movieList")
    private List<Character>characterList;
    @ManyToMany
    @JoinTable(name="genresPerMovie",joinColumns = @JoinColumn(name = "movieId"),inverseJoinColumns = @JoinColumn(name="genreId"))
    private List<Genre>genreList;

}
