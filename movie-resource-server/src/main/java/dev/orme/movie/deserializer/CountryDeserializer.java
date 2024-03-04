package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.orme.movie.entity.Country;

import java.io.IOException;

public class CountryDeserializer extends StdDeserializer<Country> {
    public CountryDeserializer() {
        this(null);
    }

    public CountryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Country deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String isoIdentifier = node.get("iso_3166_1").asText();
        String name = node.get("name").asText();
        Country country = new Country();
        country.setIsoIdentifier(isoIdentifier);
        country.setName(name);
        return country;
    }
}
