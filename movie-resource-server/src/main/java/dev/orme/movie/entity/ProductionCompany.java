package dev.orme.movie.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity

public class ProductionCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int tmdbId;
    private String logoPath;
    private String name;
    @ManyToOne
    @JoinColumn(name="country_id")
    private Country originCountry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(Country originCountry) {
        this.originCountry = originCountry;
    }
}
