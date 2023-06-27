package pl.kartven.javaproapp.data.model.domain;

import java.io.Serializable;

import pl.kartven.javaproapp.data.model.api.SlideApi;

public class SlideDomain implements Serializable {
    private Long id;
    private String title;
    private byte[] imageBytes;

    public SlideDomain(Long id, String title, byte[] imageBytes) {
        this.id = id;
        this.title = title;
        this.imageBytes = imageBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public static SlideDomain map(SlideApi slideApi) {
        return new SlideDomain(slideApi.getId(), slideApi.getTitle(), slideApi.getContent());
    }
}
