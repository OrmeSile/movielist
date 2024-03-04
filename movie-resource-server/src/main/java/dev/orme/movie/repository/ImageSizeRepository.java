package dev.orme.movie.repository;

import dev.orme.movie.entity.ImageSize;
import dev.orme.movie.entity.keys.ImageSizeCompositeKey;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ImageSizeRepository extends CrudRepository<ImageSize, ImageSizeCompositeKey> {
    @Cacheable(value = "imageSize")
    Optional<Set<ImageSize>> findAllByKey(String key);

}
