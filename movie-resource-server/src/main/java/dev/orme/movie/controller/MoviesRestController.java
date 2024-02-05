package dev.orme.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import dev.orme.movie.entity.Movie;
import dev.orme.movie.repository.MovieRepository;
import dev.orme.movie.DTO.MovieSearchRequest;
import dev.orme.movie.properties.TMDBProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path = "/movies")
public class MoviesRestController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TMDBProperties TMDBProperties;
    private static final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @PostMapping(path = "/add", consumes = "application/json")
    public @ResponseBody String addNewMovie(@RequestParam String title) {
        Movie movie = new Movie();
        movie.setOriginalTitle(title);
        movieRepository.save(movie);
        return "Saved";
    }
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    //https://www.baeldung.com/spring-5-webclient
    @GetMapping(path = "/get")
    public @ResponseBody Movie getMovie(@RequestParam int id) {
        Optional<Movie> movie = movieRepository.findByTmdbId(id);
        if (movie.isPresent()) return movie.get();

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", TMDBProperties.getToken()))
                .defaultUriVariables(Collections.singletonMap("url", "https://api.themoviedb.org/3/movie/"))
                .build();

        var uriSpec = webClient.get();
        var headersSpec = uriSpec.uri(String.valueOf(id));

        WebClient.ResponseSpec responseSpec = headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        Mono<Object> res = headersSpec.exchangeToMono((ClientResponse response) -> response.bodyToMono(Object.class));
        Movie fetchedMovie = mapper.convertValue(res.block(), Movie.class);
        return movieRepository.save(fetchedMovie);
    }

    @GetMapping(path = "/find")
    public @ResponseBody Iterable<Movie> findMovie(@RequestParam("query") String query) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/search/movie")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", TMDBProperties.getToken()))
                .defaultUriVariables(Collections.singletonMap("url", "https://api.themoviedb.org/3/search/movie"))
                .build();

        var uriSpec = webClient.get();
        var headersSpec = uriSpec.uri(uriBuilder -> uriBuilder.queryParam("query", query).build());
        var responseSpec = headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        Mono<Object> res = responseSpec.exchangeToMono((ClientResponse response) -> response.bodyToMono(Object.class));
        var results = mapper.convertValue(res.block(), MovieSearchRequest.class).results();
        return movieRepository.saveAll(results);
    }
}
