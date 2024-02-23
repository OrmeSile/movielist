package dev.orme.movie.deserializer;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import dev.orme.movie.entity.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.Set;
//
//public class MovieDeserializer extends StdDeserializer<Movie> {
//    private static final ObjectMapper mapper = new ObjectMapper();
//    private  static final Logger logger = LoggerFactory.getLogger(MovieDeserializer.class);
//    public MovieDeserializer() {
//        this(null);
//    }
//
//    public MovieDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public Movie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        JsonNode node = p.getCodec().readTree(p);
//        String original_title = node.get("original_title").asText();
//        int tmdbId = (Integer) (node.get("id")).numberValue();
//        String title = node.get("title").asText();
//        boolean adult = node.get("adult").asBoolean();
//        String backdrop_path = node.get("backdrop_path").asText();
//        String belongs_to_collection = node.get("belongs_to_collection").asText();
//        int budget = (Integer) node.get("budget").numberValue();
//        String homepage = node.get("homepage").asText();
//        String imdbId = node.get("imdb_id").asText();
//        String overview = node.get("overview").asText();
//        Float popularity = node.get("popularity").numberValue().floatValue();
//        String poster_path = node.get("poster_path").asText();
//        LocalDate release_date = LocalDate.parse(node.get("release_date").asText());
//        int revenue = (Integer) node.get("revenue").numberValue();
//        String status = node.get("status").asText();
//        String tagline = node.get("tagline").asText();
//        boolean video = node.get("video").asBoolean();
//        Float vote_average = node.get("vote_average").numberValue().floatValue();
//        int voteCount = (Integer) node.get("vote_count").numberValue();
//        int runtime = (Integer) node.get("runtime").numberValue();
//        String genresString = node.get("genres").toString();
//        String productionCompaniesString = node.get("production_companies").toString();
//        String spokenLanguagesString = node.get("spoken_languages").toString();
//        String productionCountriesString = node.get("production_countries").toString();
//
//        Set<Genre> genres = mapper.readValue(genresString, new TypeReference<Set<Genre>>() {});
//        Set<ProductionCompany> production_companies = mapper.readValue(productionCompaniesString, new TypeReference<Set<ProductionCompany>>() {});
//        Set<Language> spoken_languages = mapper.readValue(spokenLanguagesString, new TypeReference<Set<Language>>() {});
//        Set<Country> production_countries = mapper.readValue(productionCountriesString, new TypeReference<Set<Country>>() {});
//
//        var movie = new Movie();
//        movie.setOriginalTitle(original_title);
//        movie.setTmdbId(tmdbId);
//        movie.setTitle(title);
//        movie.setAdult(adult);
//        movie.setBackdropPath(backdrop_path);
//        movie.setBelongsToCollection(belongs_to_collection);
//        movie.setBudget(budget);
//        movie.setHomepage(homepage);
//        movie.setImdbId(imdbId);
//        movie.setOverview(overview);
//        movie.setPopularity(popularity);
//        movie.setPosterPath(poster_path);
//        movie.setReleaseDate(release_date);
//        movie.setRevenue(revenue);
//        movie.setStatus(status);
//        movie.setTagline(tagline);
//        movie.setVideo(video);
//        movie.setVoteAverage(vote_average);
//        movie.setVoteCount(voteCount);
//        movie.setRuntime(runtime);
//        movie.setGenres(genres);
//        movie.setProductionCompanies(production_companies);
//        movie.setSpokenLanguages(spoken_languages);
//        movie.setProductionCountries(production_countries);
//        return movie;
//    }
//}
