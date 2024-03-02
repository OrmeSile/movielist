package dev.orme.movie.repository;

import dev.orme.movie.entity.Country;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CountryRepository extends ReactiveCrudRepository<Country, String> {
    Mono<Country> findByIsoIdentifier(String isoIdentifier);
}
