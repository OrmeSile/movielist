package dev.orme.movie.entity;

import dev.orme.movie.entity.configuration.ImageSizeCompositeKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(ImageSizeCompositeKey.class)
public class ImageSize {
    @Id
    String key;
    @Id
    int size;
    String value;

    public ImageSize() {
    }

    public ImageSize(String key, int size, String value) {
        this.key = key;
        this.size = size;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
