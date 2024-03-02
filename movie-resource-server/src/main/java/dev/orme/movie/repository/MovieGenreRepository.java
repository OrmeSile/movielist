package dev.orme.movie.repository;

import dev.orme.movie.entity.MovieGenre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface MovieGenreRepository extends ReactiveCrudRepository<MovieGenre, String> {
    Mono<MovieGenre> findByTmdbId(int id);
    Mono<MovieGenre> findByName(String name);
}
