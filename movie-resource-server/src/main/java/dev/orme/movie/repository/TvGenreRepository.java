package dev.orme.movie.repository;

import dev.orme.movie.entity.TvGenre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TvGenreRepository extends CrudRepository<TvGenre, String> {
    Optional<TvGenre> findByTmdbId(int id);
    Optional<TvGenre> findByName(String name);
}
