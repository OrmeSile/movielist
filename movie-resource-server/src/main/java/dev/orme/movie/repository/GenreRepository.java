package dev.orme.movie.repository;

import dev.orme.movie.entity.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, String> {
    Optional<Genre> findByTmdbId(int id);
    Optional<Genre> findByName(String name);
}
