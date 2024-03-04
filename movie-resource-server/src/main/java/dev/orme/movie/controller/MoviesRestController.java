package dev.orme.movie.controller;

import dev.orme.movie.entity.*;
import dev.orme.movie.repository.*;
import dev.orme.movie.api.TMDBApi;
import dev.orme.movie.utils.PageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;

@RestController
@RequestMapping("/movies")
public class MoviesRestController {
    private final MovieRepository movieRepository;
    private final TMDBApi TMDBApi;

    public MoviesRestController(MovieRepository movieRepository, TMDBApi TMDBApi) {
        this.movieRepository = movieRepository;
        this.TMDBApi = TMDBApi;
    }

    @GetMapping(path = "", produces = "application/json")
    public PageResponse<List<Movie>> getAllMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int pageSize) {
        if (pageSize > 200)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page size must be inferior or equal to 200.");
        if (pageSize < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page size must not be negative");
        if (page < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page number must not be negative.");
        Slice<Movie> movieSlice = movieRepository.findAllByOrderByTmdbIdAsc(PageRequest.of(page, pageSize));
        if (movieSlice.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "page not found");
        List<Movie> movies = movieSlice.getContent();
        List<Movie> updatedMovies = movies.stream().map(movie -> {
                    if (movie.isUpdated()) return movie;
                    Optional<Movie> apiMovie = TMDBApi.getMovieById(movie.getTmdbId());
                    if (apiMovie.isEmpty()) return movie;
                    apiMovie.get().setUuid(movie.getUuid());
                    return movieRepository.save(apiMovie.get());
                }
        ).toList();
        return new PageResponse<>(
                updatedMovies,
                movieSlice.previousOrFirstPageable().getPageNumber(),
                movieSlice.nextOrLastPageable().getPageNumber(),
                movieSlice.getNumberOfElements()
        );
    }

    //pseudo-reactive / async jpa. TODO convert all controller methods

    @GetMapping(path = "/{id}", produces = "application/json")
    public Movie getMovie(@PathVariable int id) {
        Optional<Movie> movie = movieRepository.findByTmdbId(id);
        if (movie.isPresent()) {
            if (movie.get().isUpdated()) return movie.get();
            Optional<Movie> movieFromApi = TMDBApi.getMovieById((movie.get().getTmdbId()));
            if (movieFromApi.isEmpty()) return movie.get();
            movieFromApi.get().setUuid(movie.get().getUuid());
            return movieRepository.save(movieFromApi.get());
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}

