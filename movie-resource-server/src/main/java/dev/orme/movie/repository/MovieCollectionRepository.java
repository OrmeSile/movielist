package dev.orme.movie.repository;

import dev.orme.movie.entity.MovieCollection;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface MovieCollectionRepository extends ReactiveCrudRepository<MovieCollection, String> {
    Mono<MovieCollection> getByName(String name);
}
