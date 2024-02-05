package dev.orme.movie.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.deserializer.MovieDeserializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonDeserialize(using = MovieDeserializer.class)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String originalTitle;
    private int tmdbId;

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTmdbId(int id) {
        this.tmdbId = id;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public String getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", tmdbId=" + tmdbId +
                '}';
    }
}
