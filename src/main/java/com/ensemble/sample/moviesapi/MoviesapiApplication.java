package com.ensemble.sample.moviesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.flywaydb.core.Flyway;

@SpringBootApplication
public class MoviesapiApplication {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/dbmovies";
		String user = "root";
		String password = "BROK123sonic";
		Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
		flyway.migrate();
		SpringApplication.run(MoviesapiApplication.class, args);
	}

}
