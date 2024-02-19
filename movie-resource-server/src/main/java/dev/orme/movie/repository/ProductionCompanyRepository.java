package dev.orme.movie.repository;

import dev.orme.movie.entity.ProductionCompany;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductionCompanyRepository extends CrudRepository<ProductionCompany, String > {
    Optional<ProductionCompany> findByTmdbId(int id);
    Optional<ProductionCompany> findByName(String name);
}
