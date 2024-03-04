package dev.orme.movie.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import dev.orme.movie.entity.Movie;
import dev.orme.movie.dto.MovieSearchRequestDTO;

import java.io.IOException;
import java.util.List;

public class MovieSearchRequestDeserializer extends StdDeserializer<MovieSearchRequestDTO> {

    private final ObjectMapper mapper;

    public MovieSearchRequestDeserializer() {
        this(null);
    }

    public MovieSearchRequestDeserializer(Class<?> vc) {
        super(vc);
        this.mapper = new ObjectMapper();
    }

    @Override
    public MovieSearchRequestDTO deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        int page = (Integer) (node.get("page")).numberValue();
        int totalPages = (Integer) (node.get("total_pages")).numberValue();
        int totalResults = (Integer) (node.get("total_results")).numberValue();
        String moviesAsString = node.get("results").toString();
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, Movie.class);
        List<Movie> asList = mapper.readValue(moviesAsString, javaType);
        return new MovieSearchRequestDTO(page, totalPages, totalResults, asList);
    }
}
