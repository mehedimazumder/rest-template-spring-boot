package com.general.template.controller;

import com.general.template.domain.dto.MovieDTO;
import com.general.template.domain.dto.UserDTO;
import com.general.template.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public MovieDTO saveFavMovie(@RequestBody MovieDTO dto, @RequestParam Long userId) throws Exception {
        return movieService.save(dto, userId);
    }
}
