package dev.orme.movie.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.entity.Genre;
import dev.orme.movie.entity.ProductionCompany;

import java.util.Set;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize
public record MovieDTO(
        String id,
        boolean adult,
        String backdropPath,
        CollectionDTO belongsToCollection,
        int budget,
        Set<GenreDTO> genres,
        String title,
        String originalTitle,
        int tmdbId,
        String homepage,
        String imdbId,
        String originalLanguage,
        String overview,
        Float popularity,
        String posterPath,
        Set<ProducerDTO> productionCompanies,
        Set<CountryDTO> productionCountries,
        String releaseDate,
        int revenue,
        int runtime,
        Set<LanguageDTO> spokenLanguages,
        String status,
        String tagline,
        boolean video,
        Float voteAverage,
        int VoteCount
) {}
