package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.deserializer.ProductionCompanyDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@JsonDeserialize(using = ProductionCompanyDeserializer.class)
public class ProductionCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column(unique = true)
    @JsonProperty("id")
    private int tmdbId;
    private String logoPath;
    private String name;
    @ManyToOne
    private Country originCountry;
    @ManyToMany(mappedBy = "productionCompanies")
    private Set<Movie> movies;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String id) {
        this.uuid = id;
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
