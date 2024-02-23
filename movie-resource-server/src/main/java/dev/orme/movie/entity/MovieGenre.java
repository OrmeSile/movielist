package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.deserializer.GenreDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column(unique = true)
    @JsonProperty("id")
    private int tmdbId;
    private String name;
    @ManyToMany(mappedBy = "movieGenres")
    private Set<Movie> movies;

    public void setUuid(String id) {
        this.uuid = id;
    }

    public String getUuid() {
        return uuid;
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
