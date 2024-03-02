package dev.orme.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.orme.movie.entity.*;
import dev.orme.movie.repository.*;
import dev.orme.movie.api.TMDBApi;
import dev.orme.movie.utils.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;

@RestController
@RequestMapping("/movies")
public class MoviesRestController {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TMDBApi TMDBApi;

    @GetMapping(path = "", produces = "application/json")
    public Mono<PageResponse<List<Movie>>> getAllMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int pageSize) {
        if (pageSize > 200)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page size must be inferior or equal to 200.");
        if (pageSize < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page size must not be negative");
        if (page < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page number must not be negative.");
        Slice<Movie> movieSlice = movieRepository.findAllByOrderByTmdbIdAsc(PageRequest.of(page, pageSize));
        if (movieSlice.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "page not found");
        Mono<List<Movie>> movies = Flux.fromIterable(movieSlice.getContent())
                .flatMap(movie -> {
                    if (!movie.isUpdated()) {
                        return TMDBApi.getMovieById(movie.getTmdbId()).publishOn(Schedulers.boundedElastic()).flatMap(movie1 -> {
                            movie1.setUuid(movie.getUuid());
                            return Mono.just(movieRepository.save(movie1));
                        });
                    }
                    return Mono.just(movie);
                }).collectList();
        return movies.map(movies1 -> new PageResponse<>(
                movies1,
                movieSlice.previousOrFirstPageable().getPageNumber(),
                movieSlice.nextOrLastPageable().getPageNumber(),
                movieSlice.getNumberOfElements()
        ));
    }

    //pseudo-reactive / async jpa. TODO convert all controller methods

    @GetMapping(path = "/{id}", produces = "application/json")
    public Mono<Movie> getMovie(@PathVariable int id) {
        return Mono.just(id)
                .publishOn(Schedulers.boundedElastic())
                .map(subId -> movieRepository.findByTmdbId(subId))
                .flatMap(movie -> {
                    if (movie.isEmpty()) {
                        return TMDBApi
                                .getMovieById(id)
                                .publishOn(Schedulers.boundedElastic())
                                .map(movie1 -> movieRepository.save(movie1));
                    } else if (!movie.get().isUpdated()) {
                        return TMDBApi
                                .getMovieById(id)
                                .map(m -> {
                                    m.setUuid(movie.get().getUuid());
                                    return m;
                                }).publishOn(Schedulers.boundedElastic())
                                .map(movie1 -> {
                                    Movie m = movieRepository.save(movie1);
                                    System.out.println(m);
                                    return m;
                                });
                    } else {
                        return Mono.just(movie.get());
                    }
                });

//            return movieFromApi.flatMap(fetchedMovie -> {
//                movie.ifPresent(value -> fetchedMovie.setUuid(value.getUuid()));
//                return Mono.just(movieRepository.save(fetchedMovie));
//            });
//        }
//        return Mono.just(movie.get());

//        );
    }
}

