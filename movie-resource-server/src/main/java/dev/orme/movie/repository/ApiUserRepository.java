package dev.orme.movie.repository;

import dev.orme.movie.entity.ApiUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApiUserRepository extends CrudRepository<ApiUser, String> {
    Optional<ApiUser> findByUsername(String username);
}
