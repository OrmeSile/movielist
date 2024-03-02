package dev.orme.movie.repository;

import dev.orme.movie.entity.ProductionCompany;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ProductionCompanyRepository extends ReactiveCrudRepository<ProductionCompany, String > {
    Flux<ProductionCompany> findByTmdbId(int id);
    Mono<ProductionCompany> findByName(String name);
}
