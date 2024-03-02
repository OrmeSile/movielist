package dev.orme.movie.repository;

import dev.orme.movie.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, String> {
    Optional<Movie> findByTmdbId(int id);
    Slice<Movie> findAllByOrderByTmdbIdAsc(Pageable page);
}
