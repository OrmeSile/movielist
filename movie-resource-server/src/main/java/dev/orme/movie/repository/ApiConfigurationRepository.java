package dev.orme.movie.repository;

import dev.orme.movie.entity.ApiConfigurationKeyValue;
import org.springframework.data.repository.CrudRepository;

public interface ApiConfigurationRepository extends CrudRepository<ApiConfigurationKeyValue, String> {
}
