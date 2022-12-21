package de.cinetastisch.backend;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.MovieService;
import de.cinetastisch.backend.service.UserService;
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
	CommandLineRunner runner(MovieService movieService, UserService userService){
		return args -> {
//			movieService.addMovie(new Movie("test Movie 1", "2020", "lo.png", "PG-18", "200 min", "Fantasy", "ka", "spoiler", "yt", "t023", "99", "6969"));
//			movieService.addMovie(new Movie("test Movie 2", "2021", "lo.png", "PG-18", "200 min", "Fantasy", "ka", "spoiler", "yt", "t023", "99", "6969"));
//			movieService.addMovie(new Movie("test Movie 3", "2022", "lo.png", "PG-18", "200 min", "Fantasy", "ka", "spoiler", "yt", "t023", "99", "6969"));

//			userService.registerUser(new User("Frank", "Leberkese", "flotte_lotte@gmx.de", "123"));
		};
	}

}
