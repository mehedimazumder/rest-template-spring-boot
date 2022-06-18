package com.general.template.service.implementation;

import com.general.template.domain.Movie;
import com.general.template.domain.User;
import com.general.template.domain.dto.MovieDTO;
import com.general.template.repository.MovieRepository;
import com.general.template.repository.UserRepository;
import com.general.template.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RedisTemplate redisTemplate;

    @Override
    public MovieDTO save(MovieDTO dto, Long userId) {
        User user = userRepository.getFirstById(userId);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", new RuntimeException("bad"));
        }

        Movie movie = movieRepository.findFirstByImdbId(dto.getImdbId());
        if(movie != null){
            movie.getUsers().add(user);
        }else{
            movie = modelMapper.map(dto, Movie.class);
        }

//        redisTemplate.opsForHash().put("MOVIE", movie.getTitle(), movie);
//        redisTemplate.opsForValue().set(movie.getImdbId(), movie);
//        redisTemplate.opsForList().rightPush(movie.getTitle(), movie);
//
//        Movie m = (Movie) redisTemplate.opsForValue().get(movie.getImdbId());
//        System.out.println(m.getTitle());
        return modelMapper.map(movieRepository.save(movie), MovieDTO.class);
    }
}
