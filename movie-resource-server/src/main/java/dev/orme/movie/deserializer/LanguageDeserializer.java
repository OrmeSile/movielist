package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.orme.movie.entity.Language;

import java.io.IOException;

public class LanguageDeserializer extends StdDeserializer<Language> {

    public LanguageDeserializer() {
        this(null);
    }

    public LanguageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Language deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String englishName = node.get("english_name").asText();
        String isoIdentifier = node.get("iso_639_1").asText();
        String name = node.get("name").asText();
        Language language = new Language();
        language.setEnglishName(englishName);
        language.setIsoIdentifier(isoIdentifier);
        language.setName(name);
        return language;
    }


}
