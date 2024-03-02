package dev.orme.movie.repository;

import dev.orme.movie.entity.ImageSize;
import dev.orme.movie.entity.keys.ImageSizeCompositeKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ImageSizeRepository extends CrudRepository<ImageSize, ImageSizeCompositeKey> {
    Optional<Set<ImageSize>> findAllByKey(String key);
}
