package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.orme.movie.entity.ProductionCompany;

import java.io.IOException;

public class ProductionCompanyDeserializer extends StdDeserializer<ProductionCompany> {
    public ProductionCompanyDeserializer() {
        this(null);
    }

    public ProductionCompanyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProductionCompany deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        int tmdbId = (Integer) node.get("id").numberValue();
        String logoPath = node.get("logo_path").asText();
        String name = node.get("name").asText();
        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setTmdbId(tmdbId);
        productionCompany.setLogoPath(logoPath);
        productionCompany.setName(name);
        return productionCompany;
    }
}
