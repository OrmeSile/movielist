package dev.orme.oauth2.keycloak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import dev.orme.oauth2.keycloak.config.KeycloakServerProperties;

@EnableConfigurationProperties({KeycloakServerProperties.class})
@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class CustomizedKeycloakApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CustomizedKeycloakApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomizedKeycloakApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties,
                                                                               KeycloakServerProperties keycloakServerProperties) {

        return (evt) -> {

            Integer port = serverProperties.getPort();
            String keycloakContextPath = keycloakServerProperties.getContextPath();

            LOG.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak", port, keycloakContextPath);
        };
    }
}
