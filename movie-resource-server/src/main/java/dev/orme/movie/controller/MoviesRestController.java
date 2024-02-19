package dev.orme.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import dev.orme.movie.entity.*;
import dev.orme.movie.repository.*;
import dev.orme.movie.DTO.MovieSearchRequest;
import dev.orme.movie.api.TMDBApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path = "/movies")
public class MoviesRestController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ProductionCompanyRepository productionCompanyRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private TMDBApi TMDBApi;
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
    public @ResponseBody Movie getMovie(@RequestParam String id) throws JsonProcessingException {
        int parsedId = Integer.parseInt(id);
        Optional<Movie> movie = movieRepository.findByTmdbId(parsedId);
        if (movie.isPresent()) return movie.get();

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", TMDBApi.getToken()))
                .defaultUriVariables(Collections.singletonMap("url", "https://api.themoviedb.org/3/movie/"))
                .build();

        var uriSpec = webClient.get();
        var headersSpec = uriSpec.uri(String.valueOf(id));

        WebClient.ResponseSpec responseSpec = headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        Mono<String> res = headersSpec.exchangeToMono((ClientResponse response) -> {
            if (!response.statusCode().is2xxSuccessful())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found");
            return response.bodyToMono(String.class);
        });
        Movie fetchedMovie = mapper.readValue(res.block(), Movie.class);
        return movieRepository.save(fetchedMovie);



//        Movie fetchedMovie = mapper.convertValue(res.block(), Movie.class);
//        if (fetchedMovie.getGenres() != null) {
//            Set<Genre> genres = fetchedMovie
//                    .getGenres().stream().map(genre -> {
//                        var dbMovie = genreRepository.findByTmdbId(genre.getTmdbId());
//                        return dbMovie.orElse(genre);
//                    }).collect(Collectors.toSet());
//            genreRepository.saveAll(genres);
//            fetchedMovie.setGenres(genres);
//        }
//        if (fetchedMovie.getProductionCompanies() != null) {
//            Set<ProductionCompany> productionCompanies = fetchedMovie
//                    .getProductionCompanies()
//                    .stream().map(productionCompany -> {
//                        var dbProductionCompany = productionCompanyRepository.findByTmdbId(productionCompany.getTmdbId());
//                        return dbProductionCompany.orElse(productionCompany);
//                    }).collect(Collectors.toSet());
//            productionCompanyRepository.saveAll(productionCompanies);
//            fetchedMovie.setProductionCompanies(productionCompanies);
//        }
//        if (fetchedMovie.getSpokenLanguages() != null) {
//            Set<Language> languages = fetchedMovie
//                    .getSpokenLanguages()
//                    .stream().map(language -> {
//                        var dbLanguage = languageRepository.findByIsoIdentifier(language.getIsoIdentifier());
//                        return dbLanguage.orElse(language);
//                    }).collect(Collectors.toSet());
//            languageRepository.saveAll(languages);
//            fetchedMovie.setSpokenLanguages(languages);
//        }
//        ;
//        if (fetchedMovie.getProductionCountries() != null) {
//            Set<Country> countries = fetchedMovie
//                    .getProductionCountries()
//                    .stream().map(country -> {
//                        var dbCountry = countryRepository.findByIsoIdentifier(country.getIsoIdentifier());
//                        return dbCountry.orElse(country);
//                    }).collect(Collectors.toSet());
//            countryRepository.saveAll(countries);
//            fetchedMovie.setProductionCountries(countries);
//        }
//        return movieRepository.save(fetchedMovie);
    }

    @GetMapping(path = "/find")
    public @ResponseBody Iterable<Movie> findMovie(@RequestParam("query") String query) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/search/movie")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", TMDBApi.getToken()))
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
