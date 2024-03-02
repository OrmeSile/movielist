package dev.orme.movie.repository;

import dev.orme.movie.entity.MovieCollection;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieCollectionRepository extends CrudRepository<MovieCollection, String> {
    Optional<MovieCollection> getByName(String name);
}
