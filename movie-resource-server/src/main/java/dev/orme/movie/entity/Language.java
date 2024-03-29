package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String englishName;
    @Column(unique = true)
    private String isoIdentifier;
    private String name;
    @ManyToMany(mappedBy = "spokenLanguages", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Movie> movies;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getIsoIdentifier() {
        return isoIdentifier;
    }

    public void setIsoIdentifier(String isoIdentifier) {
        this.isoIdentifier = isoIdentifier;
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
