package dev.orme.movie.entity.keys;

import java.io.Serializable;
import java.util.Objects;

public class ImageSizeCompositeKey implements Serializable {
    private String key;
    private int size;

    public ImageSizeCompositeKey(String key, int size) {
        this.key = key;
        this.size = size;
    }

    public ImageSizeCompositeKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageSizeCompositeKey that = (ImageSizeCompositeKey) o;
        return Objects.equals(key, that.key) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, size);
    }
}
