package com.ensemble.sample.moviesapi.repository;

import com.ensemble.sample.moviesapi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
