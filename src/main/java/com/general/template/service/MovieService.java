package com.general.template.service;

import com.general.template.domain.Movie;
import com.general.template.domain.dto.MovieDTO;

import java.util.List;

public interface MovieService {
    MovieDTO save(MovieDTO dto, Long userId);
}
