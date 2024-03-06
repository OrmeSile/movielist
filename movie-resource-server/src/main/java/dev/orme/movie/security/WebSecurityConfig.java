package dev.orme.movie.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    @Bean
//    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
//        return http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/movies/**"))
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .cors(cors -> cors.configurationSource(corsWebFilter()))
//                .authorizeExchange((exchanges) -> exchanges
//                        .pathMatchers(HttpMethod.GET, "/movies/**" )
//                        .access(hasScope("read"))
//                        .pathMatchers(HttpMethod.POST, "/movies/**")
//                        .access(hasScope("write"))
//                        .pathMatchers("/**")
//                        .permitAll()
//                        .anyExchange().authenticated()
//                ).oauth2ResourceServer(oauth2 ->oauth2.jwt(Customizer.withDefaults()))
//                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/movies/**")
                        .access(hasScope("read"))
                        .requestMatchers(HttpMethod.POST, "/movies/**")
                        .access(hasScope("write"))
                        .requestMatchers(HttpMethod.GET, "/configuration/**")
                        .access(hasScope("read"))
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://frontend:3000"));
        corsConfiguration.setMaxAge(8000L);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", ""));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    }
