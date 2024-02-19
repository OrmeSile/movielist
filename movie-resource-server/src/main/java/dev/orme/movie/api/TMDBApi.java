package dev.orme.movie.api;

import dev.orme.movie.DTO.MovieDTO;
import dev.orme.movie.entity.Movie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TMDBApi {
    private final String token;
    private String baseUrl;
    private WebClient webClient;

    public TMDBApi() {
        this.baseUrl = "https://api.themoviedb.org/3/";
        Path path = Paths.get("/run/secrets/tmdb_token");
        try (Stream<String> lines = Files.lines(path)) {
            this.token = lines.collect(Collectors.joining("\n"));
            this.webClient = WebClient.builder()
                    .baseUrl(this.baseUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", this.token))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<Movie> getMovieById(int id) {
        var movieDTO = webClient.get()
                .uri("movies/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MovieDTO.class);
        var movie = movieDTO.flatMap(content ->{

                })
    }
}

