package com.ensemble.sample.moviesapi.controller;

import com.ensemble.sample.moviesapi.event.ResourceCreatedEvent;
import com.ensemble.sample.moviesapi.model.Movie;
import com.ensemble.sample.moviesapi.repository.MoviesRepository;
import com.ensemble.sample.moviesapi.service.MovieService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MoviesRepository moviesRepository;
    private ApplicationEventPublisher publisher;
    private MovieService service;

    public MovieController(MoviesRepository moviesRepository, ApplicationEventPublisher publisher, MovieService service) {
        this.moviesRepository = moviesRepository;
        this.publisher = publisher;
        this.service = service;
    }

    @GetMapping("/filter")
    public List<Movie> moviesList(@RequestParam String title){
        return this.moviesRepository.findByTitleContainingIgnoreCase(title);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movies, HttpServletResponse response){
        Movie saveMovies = moviesRepository.save(movies);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, saveMovies.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMovies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id){
        return this.moviesRepository.findById(id)
                .map(movies -> ResponseEntity.ok(movies))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id){
        this.moviesRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movie){
        return this.service.updateMovie(id, movie);
    }

    @PutMapping("/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLike(@PathVariable Long id){
        this.service.updateLike(id);
    }

    @PutMapping("/{id}/dislike")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDislike(@PathVariable Long id){
        this.service.updateDislike(id);
    }

}
