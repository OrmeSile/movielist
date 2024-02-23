package dev.orme.movie.entity;

import jakarta.persistence.*;

@Entity
public class TvGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column(unique = true)
    private int tmdbId;
    private String name;

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
