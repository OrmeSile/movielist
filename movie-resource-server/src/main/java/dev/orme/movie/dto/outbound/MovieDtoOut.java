package dev.orme.movie.dto.outbound;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record MovieDtoOut(String uuid, String backdrop_path, String poster_path, String title, String tmdbId, String overview) {
}
