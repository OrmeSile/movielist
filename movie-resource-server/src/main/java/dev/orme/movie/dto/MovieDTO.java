package dev.orme.movie.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Set;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize
public record MovieDTO(
        int id,
        boolean adult,
        String backdrop_path,
        CollectionDTO belongs_to_collection,
        int budget,
        Set<GenreDTO> genres,
        String title,
        String original_title,
        String homepage,
        String original_language,
        String overview,
        Float popularity,
        String poster_path,
        Set<ProducerDTO> production_companies,
        Set<CountryDTO> production_countries,
        String release_date,
        int revenue,
        int runtime,
        Set<LanguageDTO> spoken_languages,
        String status,
        String tagline,
        boolean video,
        Float vote_average,
        int vote_count,
        String imdb_id
) {}
