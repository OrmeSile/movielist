package dev.orme.movie.dto.inbound;

import java.util.List;

public record ImagesConfigurationDTO(
        String base_url,
        String secure_base_url,
        List<String> backdrop_sizes,
        List<String> logo_sizes,
        List<String> poster_sizes,
        List<String> profile_sizes,
        List<String> still_sizes
        ) {}
