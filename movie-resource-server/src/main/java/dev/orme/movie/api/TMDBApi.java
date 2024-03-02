package dev.orme.movie.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.orme.movie.DTO.*;
import dev.orme.movie.entity.*;
import dev.orme.movie.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TMDBApi {
    private final String token;
    private final String baseUrl;
    private final WebClient webClient;
    private final int BATCH_SIZE = 3000;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ProductionCompanyRepository productionCompanyRepository;
    @Autowired
    private MovieGenreRepository movieGenreRepository;
    @Autowired
    private TvGenreRepository tvGenreRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieCollectionRepository movieCollectionRepository;
    @Autowired
    private ImageSizeRepository imageSizeRepository;
    @Autowired
    ApiConfigurationRepository apiConfigurationRepository;
    @Autowired
    private ObjectMapper mapper;
    private Logger logger;


    public TMDBApi() {
        this.logger = LoggerFactory.getLogger(TMDBApi.class);
        this.baseUrl = "https://api.themoviedb.org/3/";
        this.token = System.getenv("TMDB_TOKEN");
        logger.warn("TMDB Token {}", this.token);
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", this.token))
                .build();
//        Path path = Paths.get("/run/secrets/tmdb_token");
//        try (Stream<String> lines = Files.lines(path)) {
//            this.token = lines.collect(Collectors.joining("\n"));
//            this.webClient = WebClient.builder()
//                    .baseUrl(this.baseUrl)
//                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", this.token))
//                    .build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Mono<Movie> getMovieById(int id) {
        var movieDTO = webClient.get()
                .uri("movie/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MovieDTO.class);
        return convertDtoToMovie(movieDTO);
    }

    @PostConstruct
    private void init() {
        Logger logger = LoggerFactory.getLogger(TMDBApi.class);
        logger.info("Starting database intialization");

        if (imageSizeRepository.count() == 0) {
            logger.info("table api_configuration is empty. fetching");
            webClient.get().uri("/configuration")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ConfigurationDTO>() {
                    }).flatMap(configurationDTO -> {
                        Set<ImageSize> imageSizes = new HashSet<>();
                        Set<ApiConfigurationKeyValue> configurationValues = new HashSet<>();
                        for (int i = 0 ; i < configurationDTO.images().backdrop_sizes().size() ; i++) {
                            imageSizes.add(new ImageSize("backdrop", i, configurationDTO.images().backdrop_sizes().get(i)));
                        }
                        for (int i = 0 ; i < configurationDTO.images().logo_sizes().size() ; i++) {
                            imageSizes.add(new ImageSize("logo", i, configurationDTO.images().logo_sizes().get(i)));
                        }
                        for (int i = 0 ; i < configurationDTO.images().poster_sizes().size() ; i++) {
                            imageSizes.add(new ImageSize("poster", i, configurationDTO.images().poster_sizes().get(i)));
                        }
                        for (int i = 0 ; i < configurationDTO.images().profile_sizes().size() ; i++) {
                            imageSizes.add(new ImageSize("profile", i, configurationDTO.images().profile_sizes().get(i)));
                        }
                        for (int i = 0 ; i < configurationDTO.images().still_sizes().size() ; i++) {
                            imageSizes.add(new ImageSize("still", i, configurationDTO.images().still_sizes().get(i)));
                        }
                        configurationValues.add(new ApiConfigurationKeyValue("insecure_image_url", configurationDTO.images().base_url()));
                        configurationValues.add(new ApiConfigurationKeyValue("image_url", configurationDTO.images().secure_base_url()));

                        return Mono.just(Tuples.of(imageSizes, configurationValues));
                    })
                    .subscribe(configurationTuple -> {
                        imageSizeRepository.saveAll(configurationTuple.getT1());
                        logger.info("saved image sizes.");
                        apiConfigurationRepository.saveAll(configurationTuple.getT2());
                        logger.info("saved image Urls.");
                    });
        }

        if (languageRepository.count() == 0) {
            logger.info("table language is empty. fetching");
            webClient.get().uri("configuration/languages")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<LanguageDTO>>() {
                    })
                    .flatMap(languageDTOS -> {
                        Set<Language> languages = languageDTOS.stream().map(languageDTO -> {
                            Language language = new Language();
                            language.setEnglishName(languageDTO.english_name());
                            language.setIsoIdentifier(languageDTO.iso_639_1());
                            language.setName(languageDTO.name());
                            return language;
                        }).collect(Collectors.toSet());
                        return Mono.just(languages);
                    }).subscribe(languages -> {
                        languageRepository.saveAll(languages);
                        logger.info("saved languages to database.");
                    });
        }

        if (countryRepository.count() == 0) {
            logger.info("table country is empty. fetching");
            webClient.get().uri("configuration/countries")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<CountryDTO>>() {
                    })
                    .flatMap(countryDTOS -> {
                        Set<Country> countries = countryDTOS.stream().map(countryDTO -> {
                            Country country = new Country();
                            country.setIsoIdentifier(countryDTO.iso_3166_1());
                            country.setName(countryDTO.english_name());
                            return country;
                        }).collect(Collectors.toSet());
                        return Mono.just(countries);
                    }).subscribe(countries -> {
                        logger.info("saved countries to database.");
                        countryRepository.saveAll(countries);
                    });
        }

        if (movieGenreRepository.count() == 0) {
            logger.info("table movie_genre is empty. fetching");
            webClient.get().uri("genre/movie/list")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GenreListRequestDTO.class)
                    .flatMap(movieGenres -> Mono.just(movieGenres.genres()))
                    .flatMap(movieGenres -> {
                        Set<MovieGenre> movieGenreSet = movieGenres.stream().map(movieGenreDTO -> {
                            MovieGenre genre = new MovieGenre();
                            genre.setName(movieGenreDTO.name());
                            genre.setTmdbId(movieGenreDTO.id());
                            return genre;
                        }).collect(Collectors.toSet());
                        return Mono.just(movieGenreSet);
                    }).subscribe(movieGenres -> {
                                movieGenreRepository.saveAll(movieGenres);
                                logger.info("saved movies genres in database.");
                            }
                    );

        }

        if (tvGenreRepository.count() == 0) {
            logger.info("table tv_genre is empty. fetching");

            webClient.get().uri("genre/tv/list")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GenreListRequestDTO.class)
                    .flatMap(tvGenres -> Mono.just(tvGenres.genres()))
                    .flatMap(tvGenres -> {
                        Set<TvGenre> tvGenresSet = tvGenres.stream().map(tvGenreDTO -> {
                            TvGenre genre = new TvGenre();
                            genre.setName(tvGenreDTO.name());
                            genre.setTmdbId(tvGenreDTO.id());
                            return genre;
                        }).collect(Collectors.toSet());
                        return Mono.just(tvGenresSet);
                    }).subscribe(tvGenres -> {
                        tvGenreRepository.saveAll(tvGenres);
                        logger.info("saved tv genres in database.");
                    });
        }
        //Reads file from path. TODO dynamic path + download
        Mono<Path> fluxFile = Mono.just(Paths.get("C:/code/react/movielist/movie-resource-server/src/main/resources/movie_ids_02_17_2024.json"));
        fluxFile.subscribe(path -> {
            long lineCount;
            try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
                lineCount = stream.count();
                logger.info("linecount is {}", lineCount);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long repoCount = movieRepository.count();
            logger.info("repoCount is {}", repoCount);
            if (lineCount > repoCount) {
                Flux<String> fluxLineReader = Flux.using(
                        () -> new FileReader("C:/code/react/movielist/movie-resource-server/src/main/resources/movie_ids_02_17_2024.json"),
                        reader -> Flux.fromStream(new BufferedReader(reader).lines().skip(repoCount)),
                        reader -> {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                logger.error("IOException : {}", e.toString());
                            }
                        });
                List<Movie> moviesToInsert = new ArrayList<>();
                fluxLineReader.subscribe(line -> {
                    try {
                        MovieLineFileImportDTO movieLineFileImportDTO = mapper.readValue(line, MovieLineFileImportDTO.class);
                        boolean movieIsPresent = movieRepository.findByTmdbId(movieLineFileImportDTO.id()).isPresent();
                        if (movieIsPresent) {
                            return;
                        }
                        Movie movie = new Movie();
                        movie.setAdult(movieLineFileImportDTO.adult());
                        movie.setTmdbId(movieLineFileImportDTO.id());
                        movie.setOriginalTitle(movieLineFileImportDTO.original_title());
                        movie.setPopularity(movieLineFileImportDTO.popularity());
                        movie.setVideo(movieLineFileImportDTO.video());
                        moviesToInsert.add(movie);
                        if (moviesToInsert.size() >= BATCH_SIZE) {
                            movieRepository.saveAll(moviesToInsert);
                            moviesToInsert.clear();
                            logger.warn("saved {} movies. Cleared list", BATCH_SIZE);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                movieRepository.saveAll(moviesToInsert);
            } else {
                logger.info("nothing to add.");
            }
        });
    }

    private Mono<Movie> convertDtoToMovie(Mono<MovieDTO> dto) {
        return dto.flatMap(movieDto -> {
            Movie movie = new Movie();
            if (movieDto.belongs_to_collection() != null) {
                Optional<MovieCollection> dbCollection = movieCollectionRepository.getByName(movieDto.belongs_to_collection().name());
                if (dbCollection.isPresent()) {
                    movie.setMovieCollection(dbCollection.get());
                } else {
                    MovieCollection collection = new MovieCollection();
                    collection.setBackdropPath(movieDto.belongs_to_collection().backdrop_path());
                    collection.setName(movieDto.belongs_to_collection().name());
                    collection.setPosterPath(movieDto.belongs_to_collection().poster_path());
                    collection.setTmdbId(movieDto.belongs_to_collection().id());
                    movie.setMovieCollection(movieCollectionRepository.save(collection));
                }
            }

            movie.setProductionCountries(movieDto.production_countries()
                    .stream()
                    .map(productionCountryDto -> {
                                var dbCountry = countryRepository.findByIsoIdentifier(productionCountryDto.iso_3166_1());
                                if (dbCountry.isPresent()) {
                                    logger.warn(dbCountry.get().toString());
                                    return dbCountry.get();
                                }
                                Country country = new Country();
                                country.setName(productionCountryDto.english_name());
                                country.setIsoIdentifier(productionCountryDto.iso_3166_1());
                                return country;
                            }
                    ).collect(Collectors.toSet()));

            movie.setProductionCompanies(movieDto.production_companies()
                    .stream()
                    .map(producerDTO -> {
                        var dbProducer = productionCompanyRepository.findByTmdbId(producerDTO.id());
                        if (dbProducer.isPresent()) return dbProducer.get();
                        ProductionCompany productionCompany = new ProductionCompany();
                        productionCompany.setName(producerDTO.name());
                        productionCompany.setLogoPath(producerDTO.logo_path());
                        productionCompany.setTmdbId(producerDTO.id());
                        productionCompany.setOriginCountry(producerDTO.origin_country());
                        return productionCompanyRepository.save(productionCompany);
                    }).collect(Collectors.toSet()));

            movie.setSpokenLanguages(movieDto.spoken_languages()
                    .stream()
                    .map(languageDTO -> {
                        var dbLanguage = languageRepository.findByIsoIdentifier(languageDTO.iso_639_1());
                        if (dbLanguage.isPresent()) return dbLanguage.get();
                        Language language = new Language();
                        language.setName(languageDTO.name());
                        language.setIsoIdentifier(languageDTO.iso_639_1());
                        language.setEnglishName(languageDTO.english_name());
                        return languageRepository.save(language);
                    }).collect(Collectors.toSet()));

            movie.setGenres(movieDto.genres()
                    .stream()
                    .map(genreDTO -> {
                        var dbGenre = movieGenreRepository.findByTmdbId(genreDTO.id());
                        if (dbGenre.isPresent()) return dbGenre.get();
                        MovieGenre movieGenre = new MovieGenre();
                        movieGenre.setName(genreDTO.name());
                        movieGenre.setTmdbId(genreDTO.id());
                        return movieGenre;
                    }).collect(Collectors.toSet()));

            movie.setAdult(movieDto.adult());
            movie.setBackdropPath(movieDto.backdrop_path());
            movie.setBudget(movieDto.budget());
            movie.setTitle(movieDto.title());
            movie.setOriginalTitle(movieDto.original_title());
            movie.setTmdbId(movieDto.id());
            movie.setImdbId(movieDto.imdb_id());
            movie.setHomepage(movieDto.homepage());
            movie.setOriginalLanguage(movieDto.original_language());
            movie.setOverview(movieDto.overview());
            movie.setPopularity(movieDto.popularity());
            movie.setPosterPath(movieDto.poster_path());
            movie.setReleaseDate(LocalDate.parse(movieDto.release_date()));
            movie.setRevenue(movieDto.revenue());
            movie.setRuntime(movieDto.runtime());
            movie.setStatus(movieDto.status());
            movie.setTagline(movieDto.tagline());
            movie.setVideo(movieDto.video());
            movie.setVoteAverage(movieDto.vote_average());
            movie.setVoteCount(movieDto.vote_count());
            movie.setUpdated(true);
            return Mono.just(movie);
        });
    }
}

