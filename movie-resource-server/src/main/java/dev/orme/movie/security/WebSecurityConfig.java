package dev.orme.movie.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/movies/**"))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsWebFilter()))
                .authorizeExchange((exchanges) -> exchanges
                        .pathMatchers(HttpMethod.GET, "/movies/**" )
                        .hasAuthority("SCOPE_read")
                        .pathMatchers(HttpMethod.POST, "/movies/**")
                        .hasAuthority("SCOPE_write")
                        .pathMatchers("/")
                        .permitAll()
                        .anyExchange().authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .formLogin(form -> form.disable())
                .build();
    }

    CorsConfigurationSource corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setMaxAge(8000L);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
