package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table("movie_genre")
public class MovieGenre {
    @Id
    private UUID uuid;
    @JsonProperty("id")
    private int tmdbId;
    private String name;
//TODO    @ManyToMany(mappedBy = "movieGenres", fetch = FetchType.LAZY)
    private Set<Movie> movies;

    public void setUuid(UUID id) {
        this.uuid = id;
    }

    public UUID getUuid() {
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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
