package dev.orme.movie.dto.inbound;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize
public record ImagePathDTO(
        float aspect_ratio,
        int height,
        String iso_639_1,
        String file_path,
        float vote_average,
        int width,
        int vote_count
) {}
