package de.cinetastisch.backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(
//		info = @Info(title = "Cinema API", version = "1.0.0"),
//		servers = {@Server(url = "http://localhost:8080")},//, @Server(url = "http://oneoone.com")},
//		tags = {@Tag(name = "Cinema", description = "This is the cinema desc.")}
//)
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
