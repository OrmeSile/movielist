package dev.orme.movie.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Table(name = "movie")
public class Movie {
    @Id
    private UUID uuid;
    private boolean adult;
    private String backdropPath;
    //    @JsonProperty("belongs_to_collection")
//TODO    @ManyToOne(cascade = CascadeType.MERGE)
    private MovieCollection movieCollection;
    private int budget;
//TODO    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "movie_movie_genres", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "movie_genres_uuid"))
    private Set<MovieGenre> movieGenres;
    private String title;
    private String originalTitle;
//    @JsonProperty("id")
    private int tmdbId;
    private String homepage;
    private String imdbId;
    private String originalLanguage;
    private String overview;
    private Float popularity;
    //    @JsonProperty("poster_path")
    private String posterPath;
//TODO    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "movie_production_companies", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "production_companies_uuid"))
    private Set<ProductionCompany> productionCompanies;
//TODO    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "movie_production_countries", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "production_countries_uuid"))
    private Set<Country> productionCountries;
    //    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private int revenue;
    private int runtime;
//TODO    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "movie_spoken_languages", joinColumns = @JoinColumn(name = "movies_uuid"), inverseJoinColumns = @JoinColumn(name = "spoken_languages_uuid"))
    private Set<Language> spokenLanguages;
    private String status;
    private String tagline;
    private boolean video;
    //    @JsonProperty("vote_average")
    private Float voteAverage;
    //    @JsonProperty("vote_count")
    private int VoteCount;
    private boolean isUpdated = false;

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

