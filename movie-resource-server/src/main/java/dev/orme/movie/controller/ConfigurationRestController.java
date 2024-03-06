package dev.orme.movie.controller;

import dev.orme.movie.entity.ApiConfigurationKeyValue;
import dev.orme.movie.entity.ImageSize;
import dev.orme.movie.repository.ApiConfigurationRepository;
import dev.orme.movie.repository.ImageSizeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/configuration")
public class ConfigurationRestController {
    private final ImageSizeRepository imageSizeRepository;
    private final ApiConfigurationRepository apiConfigurationRepository;

    public ConfigurationRestController(ImageSizeRepository imageSizeRepository, ApiConfigurationRepository apiConfigurationRepository) {
        this.imageSizeRepository = imageSizeRepository;
        this.apiConfigurationRepository = apiConfigurationRepository;
    }

    @GetMapping(path ="/images", produces = "application/json")
    public Iterable<ImageSize> getAllImageSizes() {
        return imageSizeRepository.findAll();
    }

    @GetMapping(path="/url", produces = "application/json")
    public Iterable<ApiConfigurationKeyValue> getAllBaseConfigurationParameters(){
        return apiConfigurationRepository.findAll();
    }
}
