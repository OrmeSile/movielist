package dev.orme.movie.repository;

import dev.orme.movie.entity.ImageSize;
import dev.orme.movie.entity.keys.ImageSizeCompositeKey;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import reactor.util.annotation.NonNull;

import java.util.Optional;
import java.util.Set;

public interface ImageSizeRepository extends CrudRepository<ImageSize, ImageSizeCompositeKey> {
    Optional<Set<ImageSize>> findAllByKey(String key);

    @Override
    @Cacheable(value = "imageSize")
    @NonNull
    Iterable<ImageSize> findAll();

}
