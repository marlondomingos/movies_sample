package com.ensemble.sample.moviesapi.service;

import com.ensemble.sample.moviesapi.model.Movie;
import com.ensemble.sample.moviesapi.repository.MoviesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private MoviesRepository moviesRepository;

    public MovieService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Movie updateMovie(Long id, Movie movie){
        Movie saveMovie = getMovies(id);
        BeanUtils.copyProperties(movie, saveMovie, "id");
        return this.moviesRepository.save(saveMovie);
    }

    public void updateLike(Long id) {
        Movie saveMovie = getMovies(id);
        saveMovie.setLike();
        this.moviesRepository.save(saveMovie);
    }

    public void updateDislike(Long id) {
        Movie saveMovie = getMovies(id);
        saveMovie.setDislike();
        this.moviesRepository.save(saveMovie);
    }

    private Movie getMovies(Long id) {
        Movie saveMovie = this.moviesRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return saveMovie;
    }
}
