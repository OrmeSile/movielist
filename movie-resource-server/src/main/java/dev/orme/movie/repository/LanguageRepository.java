package dev.orme.movie.repository;

import dev.orme.movie.entity.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguageRepository extends CrudRepository<Language, String> {
    Optional<Language> findByIsoIdentifier(String isoIdentifier);
    Optional<Language> findByEnglishName(String name);
}
