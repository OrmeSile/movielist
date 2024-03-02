package dev.orme.movie.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.orme.movie.deserializer.MovieSearchRequestDeserializer;
import dev.orme.movie.entity.Movie;

import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize(using = MovieSearchRequestDeserializer.class)
public record MovieSearchRequestDTO(int page, int totalPages, int totalResults, List<Movie> results) {}
