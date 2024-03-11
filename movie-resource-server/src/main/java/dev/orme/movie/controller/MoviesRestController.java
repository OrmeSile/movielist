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
    public PageResponse<List<Movie>> getAllMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int pageSize) {
        if (pageSize > 50)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page size must be inferior or equal to 50.");
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

    @GetMapping(path = "/{id}", produces = "application/json")
    public Movie getMovie(@PathVariable int id) {
        Optional<Movie> optionalMovie = movieRepository.findByTmdbId(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            if(movie.isUpdated() && movie.getImages() != null) return movie;
            Optional<Movie> movieFromApi = TMDBApi.getMovieById(movie.getTmdbId());
            if(movieFromApi.isEmpty()) return movie;
            Movie apiMovie = movieFromApi.get();
            apiMovie.setUuid(movie.getUuid());
            if(apiMovie.getImages() == null) TMDBApi.getImagesForMovieByTmdbId(movie.getTmdbId()).ifPresent(movie::setImages);
            return movieRepository.save(movieFromApi.get());
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}

