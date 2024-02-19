package dev.orme.movie.repository;

import dev.orme.movie.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, String> {
    Optional<Country> findByIsoIdentifier(String isoIdentifier);
}
