package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.orme.movie.entity.Genre;

import java.io.IOException;

public class GenreDeserializer extends StdDeserializer<Genre> {
    public GenreDeserializer() {
        this(null);
    }

    public GenreDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Genre deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        int tmdbId = (Integer) node.get("id").numberValue();
        String name = node.get("name").asText();
        Genre genre = new Genre();
        genre.setTmdbId(tmdbId);
        genre.setName(name);
        return genre;
    }
}
