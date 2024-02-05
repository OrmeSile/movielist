package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.orme.movie.entity.Movie;

import java.io.IOException;

public class MovieDeserializer extends StdDeserializer<Movie> {

    public MovieDeserializer() {
        this(null);
    }

    public MovieDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Movie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        int tmdbId = (Integer) (node.get("id")).numberValue();
        String originalTitle = node.get("original_title").asText();
        String title = node.get("title").asText();
        var movie = new Movie();
        movie.setOriginalTitle(originalTitle);
        movie.setTmdbId(tmdbId);
        movie.setTitle(title);
        return movie;
    }
}
