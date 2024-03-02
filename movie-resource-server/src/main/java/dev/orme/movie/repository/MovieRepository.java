package dev.orme.movie.repository;

import dev.orme.movie.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface MovieRepository extends ReactiveCrudRepository<Movie, String> {
    Mono<Movie> findByTmdbId(int id);
    Flux<Movie> findAllByOrderByTmdbIdAsc(Pageable page);
}
