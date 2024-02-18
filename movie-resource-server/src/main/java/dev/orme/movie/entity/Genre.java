package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int tmdbId;
    private String name;
    @ManyToMany(targetEntity = Movie.class)
    private Set<Movie> movies;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
