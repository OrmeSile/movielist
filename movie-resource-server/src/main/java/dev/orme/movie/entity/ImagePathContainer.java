package dev.orme.movie.entity;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class ImagePathContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private Set<ImagePath> posters;
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private Set<ImagePath> backdrops;
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private Set<ImagePath> logos;
    @OneToOne(mappedBy = "images", cascade = CascadeType.ALL)
    private Movie movie;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Set<ImagePath> getPosters() {
        return posters;
    }

    public void setPosters(Set<ImagePath> posters) {
        this.posters = posters;
    }

    public Set<ImagePath> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(Set<ImagePath> backdrops) {
        this.backdrops = backdrops;
    }

    public Set<ImagePath> getLogos() {
        return logos;
    }

    public void setLogos(Set<ImagePath> logos) {
        this.logos = logos;
    }
}
