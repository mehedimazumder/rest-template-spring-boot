package com.general.template.service;

import com.general.template.common.exception.BadRequestException;
import com.general.template.domain.Movie;
import com.general.template.domain.dto.MovieDTO;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;

public interface MovieService {
    MovieDTO save(MovieDTO dto, Long userId) throws Exception;

    Page<List<Movie>> getAll(int page, int size, String sortBy);
}
