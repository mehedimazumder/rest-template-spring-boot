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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RedisService redisService;
    private final AsyncService asyncService;

    @Override
    public MovieDTO save(MovieDTO dto, Long userId) throws Exception {
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

        // async
        CompletableFuture<Object> employeeAddress = asyncService.getEmployeeEmail();
        CompletableFuture<Object> employeeName = asyncService.getEmployeeName();

        // Wait until they are all done
        CompletableFuture.allOf(employeeAddress, employeeName);

        employeeAddress.get();
        employeeName.get();
        //end

//        redisService.put("MOVIE", movie.getTitle(), movie);
        redisService.put(movie.getImdbId(), movie);
//        redisTemplate.opsForList().rightPush(movie.getTitle(), movie);
//
//        Movie m = (Movie) redisTemplate.opsForValue().get(movie.getImdbId());
//        System.out.println(m.getTitle());
        return modelMapper.map(movieRepository.save(movie), MovieDTO.class);
    }

    @Override
    public Page<List<Movie>> getAll(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        return movieRepository.findAllByTitleNotNull(paging);
    }
}
