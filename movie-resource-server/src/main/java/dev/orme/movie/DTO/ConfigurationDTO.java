package dev.orme.movie.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Set;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize
public record ConfigurationDTO(ImagesConfigurationDTO images, Set<String> change_keys) {
}

record ImagesConfigurationDTO(
        String base_url,
        String secure_base_url,
        List<String> backdrop_sizes,
        List<String> logo_sizes,
        List<String> poster_sizes,
        List<String> profile_sizes,
        List<String> still_sizes
        ) {}
