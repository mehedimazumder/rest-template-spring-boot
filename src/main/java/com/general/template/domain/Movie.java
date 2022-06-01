package com.general.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name= "movies", indexes = {@Index(name = "titleIdx", columnList = "title", unique = true)})
public class Movie extends BaseEntity {
    @NotNull
    private String title;
    private String genre;
    private String released;
    private Double imdbRating;
    private String poster;
    @Column(nullable = false, unique = true, length = 100)
    private String imdbId;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<User> users = new HashSet<>();
}
