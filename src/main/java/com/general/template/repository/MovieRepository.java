package com.general.template.repository;

import com.general.template.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findFirstByImdbId(String imdbId);
    boolean existsByImdbId(String imdbId);
    List<Movie> findAllByTitleContaining(String title);
    Page<List<Movie>> findAllByTitleNotNull(Pageable page);
}
