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
//        String originalTitle = node.get("original_title").asText();
//        int tmdbId = (Integer) (node.get("id")).numberValue();
//        String title = node.get("title").asText();
//        boolean adult = node.get("adult").asBoolean();
//        String backdropPath = node.get("backdrop_path").asText();
//        String belongsToCollection = node.get("belongs_to_collection").asText();
//        int budget = (Integer) node.get("budget").numberValue();
//        String homepage = node.get("homepage").asText();
//        String imdbId = node.get("imdb_id").asText();
//        String overview = node.get("overview").asText();
//        Float popularity = node.get("popularity").numberValue().floatValue();
//        String posterPath = node.get("poster_path").asText();
//        LocalDate releaseDate = LocalDate.parse(node.get("release_date").asText());
//        int revenue = (Integer) node.get("revenue").numberValue();
//        String status = node.get("status").asText();
//        String tagline = node.get("tagline").asText();
//        boolean video = node.get("video").asBoolean();
//        Float voteAverage = node.get("vote_average").numberValue().floatValue();
//        int voteCount = (Integer) node.get("vote_count").numberValue();
//        int runtime = (Integer) node.get("runtime").numberValue();
//        String genresString = node.get("genres").toString();
//        String productionCompaniesString = node.get("production_companies").toString();
//        String spokenLanguagesString = node.get("spoken_languages").toString();
//        String productionCountriesString = node.get("production_countries").toString();
//
//        Set<Genre> genres = mapper.readValue(genresString, new TypeReference<Set<Genre>>() {});
//        Set<ProductionCompany> productionCompanies = mapper.readValue(productionCompaniesString, new TypeReference<Set<ProductionCompany>>() {});
//        Set<Language> spokenLanguages = mapper.readValue(spokenLanguagesString, new TypeReference<Set<Language>>() {});
//        Set<Country> productionCountries = mapper.readValue(productionCountriesString, new TypeReference<Set<Country>>() {});
//
//        var movie = new Movie();
//        movie.setOriginalTitle(originalTitle);
//        movie.setTmdbId(tmdbId);
//        movie.setTitle(title);
//        movie.setAdult(adult);
//        movie.setBackdropPath(backdropPath);
//        movie.setBelongsToCollection(belongsToCollection);
//        movie.setBudget(budget);
//        movie.setHomepage(homepage);
//        movie.setImdbId(imdbId);
//        movie.setOverview(overview);
//        movie.setPopularity(popularity);
//        movie.setPosterPath(posterPath);
//        movie.setReleaseDate(releaseDate);
//        movie.setRevenue(revenue);
//        movie.setStatus(status);
//        movie.setTagline(tagline);
//        movie.setVideo(video);
//        movie.setVoteAverage(voteAverage);
//        movie.setVoteCount(voteCount);
//        movie.setRuntime(runtime);
//        movie.setGenres(genres);
//        movie.setProductionCompanies(productionCompanies);
//        movie.setSpokenLanguages(spokenLanguages);
//        movie.setProductionCountries(productionCountries);
//        return movie;
//    }
//}
