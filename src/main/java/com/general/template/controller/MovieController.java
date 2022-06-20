package com.general.template.controller;

import com.general.template.domain.Movie;
import com.general.template.domain.dto.MovieDTO;
import com.general.template.domain.dto.UserDTO;
import com.general.template.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public MovieDTO saveFavMovie(@RequestBody MovieDTO dto, @RequestParam Long userId) throws Exception {
        return movieService.save(dto, userId);
    }

    @GetMapping
    public Page<List<Movie>> getMovies(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "1") int size,
                                       @RequestParam(defaultValue = "title") String sortBy) throws Exception {
        return movieService.getAll(page, size, sortBy);
    }
}
