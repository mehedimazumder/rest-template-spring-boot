package com.general.template.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private String released;
    private Double imdbRating;
    private String poster;
    @NotNull
    private String imdbId;

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String genre, String released, Double imdbRating, String poster, @NotNull String imdbId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.released = released;
        this.imdbRating = imdbRating;
        this.poster = poster;
        this.imdbId = imdbId;
    }
}
