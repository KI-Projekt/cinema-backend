package de.cinetastisch.backend;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.service.MovieService;
import de.cinetastisch.backend.service.RoomService;
import de.cinetastisch.backend.service.ScreeningService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAzureCinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAzureCinemaApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(MovieService movieService,
//							 ScreeningService screeningService,
//							 RoomService roomService){
//		return args -> {
//			Movie testMovie = movieService.addMovie(new Movie("test Movie 1", "2020", "lo.png", "PG-18", "200 min", "Fantasy", "ka", "spoiler", "yt", "t023", "99", "6969"));
//			Room testRoom = roomService.addRoom(false, false);
//			Screening screening = screeningService.addScreening("01.01.2023","1", 1L, 1L);
//		};
//	}

}
