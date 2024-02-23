package dev.orme.movie.repository;

import dev.orme.movie.entity.MovieGenre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieGenreRepository extends CrudRepository<MovieGenre, String> {
    Optional<MovieGenre> findByTmdbId(int id);
    Optional<MovieGenre> findByName(String name);
}
