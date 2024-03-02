package dev.orme.movie.repository;

import dev.orme.movie.entity.TvGenre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface TvGenreRepository extends ReactiveCrudRepository<TvGenre, String> {
    Mono<TvGenre> findByTmdbId(int id);
    Mono<TvGenre> findByName(String name);
}
