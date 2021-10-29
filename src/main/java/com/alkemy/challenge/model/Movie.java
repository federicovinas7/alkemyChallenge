package com.alkemy.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String image;
    private Date creationDate;
    private Integer qualification;
    @ManyToMany(mappedBy = "movieList",fetch = FetchType.EAGER)
    private Set<Character>characterList;
    @ManyToOne(cascade=CascadeType.PERSIST) @JoinColumn(name="genreId",foreignKey = @ForeignKey (name = "FK_movies_genres"))
    Genre genre;

}
