package dev.orme.movie.dto.inbound;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Set;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize
public record ConfigurationDTO(ImagesConfigurationDTO images, Set<String> change_keys) {
}

