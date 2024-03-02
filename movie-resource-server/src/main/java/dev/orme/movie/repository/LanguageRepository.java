package dev.orme.movie.repository;

import dev.orme.movie.entity.Language;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface LanguageRepository extends ReactiveCrudRepository<Language, String> {
    Mono<Language> findByIsoIdentifier(String isoIdentifier);
    Mono<Language> findByEnglishName(String name);
}
