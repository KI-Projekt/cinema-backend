package de.cinetastisch.backend;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAzureCinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAzureCinemaApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(MovieService movieService){
		return args -> {
			movieService.addMovie(new Movie());
			movieService.addMovie(new Movie());
			movieService.addMovie(new Movie());
		};
	}

}
