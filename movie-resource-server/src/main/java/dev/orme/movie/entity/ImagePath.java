package dev.orme.movie.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="image_path")
public class ImagePath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private float aspectRatio;
    private int height;
    private String isoIdentifier;
    private String filePath;
    private float voteAverage;
    private int voteCount;
    private int width;
    @ManyToOne
    @JoinColumn(name="container_uuid")
    private ImagePathContainer container;

    public ImagePathContainer getContainer() {
        return container;
    }

    public void setContainer(ImagePathContainer container) {
        this.container = container;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getIsoIdentifier() {
        return isoIdentifier;
    }

    public void setIsoIdentifier(String isoIdentifier) {
        this.isoIdentifier = isoIdentifier;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
