package dev.orme.movie.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table("country")
@JsonIgnoreProperties("movies")
public class Country {
    @Id
    private UUID uuid;
    @JsonAlias("iso_3166_1")
    private String isoIdentifier;
    private String name;
//TODO    @ManyToMany(mappedBy = "productionCountries")
//    @JsonIgnore
    private Set<Movie> movies;

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID id) {
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

    @Override
    public String toString() {
        return "Country{" +
                "uuid=" + uuid +
                ", isoIdentifier='" + isoIdentifier + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
