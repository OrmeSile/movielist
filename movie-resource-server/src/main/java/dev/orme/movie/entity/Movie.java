package dev.orme.movie.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "movie", indexes = {@Index(columnList = "release_date ASC", unique = true), @Index(columnList = "release_date DESC", unique = true), @Index(columnList = "popularity ASC"), @Index(columnList = "popularity DESC"), @Index(columnList = "vote_average ASC"), @Index(columnList = "vote_average DESC"), @Index(columnList = "original_title ASC"), @Index(columnList = "original_title DESC")})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private boolean adult;
    private String backdropPath;
    @ManyToOne(cascade = CascadeType.MERGE)
    private MovieCollection movieCollection;
    private int budget;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_movie_genres", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "movie_genres_uuid"))
    private Set<MovieGenre> movieGenres;
    private String title;
    private String originalTitle;
    @Column(unique = true)
    private int tmdbId;
    private String homepage;
    private String imdbId;
    private String originalLanguage;
    @Column(columnDefinition = "TEXT")
    private String overview;
    private Float popularity;
    private String posterPath;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_production_companies", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "production_companies_uuid"))
    private Set<ProductionCompany> productionCompanies;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_production_countries", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "production_countries_uuid"))
    private Set<Country> productionCountries;
    private LocalDate releaseDate;
    private int revenue;
    private int runtime;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_spoken_languages", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "spoken_languages_uuid"))
    private Set<Language> spokenLanguages;
    private String status;
    private String tagline;
    private boolean video;
    private Float voteAverage;
    private int VoteCount;
    private boolean isUpdated = false;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_container_id", referencedColumnName = "uuid")
    private ImagePathContainer images;

    public ImagePathContainer getImages() {
        return images;
    }

    public void setImages(ImagePathContainer container) {
        this.images = container;
    }

    public Set<MovieGenre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(Set<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Set<MovieGenre> getGenres() {
        return movieGenres;
    }

    public void setGenres(Set<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Set<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(Set<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public Set<Country> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(Set<Country> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public MovieCollection getMovieCollection() {
        return movieCollection;
    }

    public void setMovieCollection(MovieCollection movieCollection) {
        this.movieCollection = movieCollection;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Set<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(Set<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return VoteCount;
    }

    public void setVoteCount(int voteCount) {
        VoteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int id) {
        this.tmdbId = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Override
    public String toString() {
        return "Movie{" + "id='" + uuid + '\'' + ", original_title='" + originalTitle + '\'' + ", tmdbId=" + tmdbId + '}';
    }
}

