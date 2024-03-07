package dev.orme.movie.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

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
                .oauth2ResourceServer(oauth2 -> oauth2.bearerTokenResolver(this::tokenExtractor).jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    public String tokenExtractor(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "ACCESS_TOKEN");
        return cookie != null ? cookie.getValue() : null;
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
