package de.cinetastisch.backend;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@OpenAPIDefinition(
		info = @Info(title = "Cinema API", version = "1.0.0"),
		servers = {@Server(url = "http://localhost:8080")},//, @Server(url = "http://asdasdasda.com")},
		tags = {
				@Tag(name = "Movies", description = "Alles über die gespeicherten Filme"),
				@Tag(name = "Rooms", description = "Alles über die Filmsäle"),
				@Tag(name = "Screenings", description = "Alles über die einzelnen Filmvorstellungen")
		}
)
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
