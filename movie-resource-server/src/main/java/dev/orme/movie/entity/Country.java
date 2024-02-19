package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.deserializer.CountryDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@JsonDeserialize(using = CountryDeserializer.class)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column(unique = true)
    @JsonAlias("iso_3166_1")
    private String isoIdentifier;
    private String name;
    @OneToMany(mappedBy = "originCountry")
    private Set<ProductionCompany> productionCompanies;
    @ManyToMany(mappedBy = "productionCountries")
    private Set<Movie> movies;

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String id) {
        this.uuid = id;
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

    public Set<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(Set<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
}
