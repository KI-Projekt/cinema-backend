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
		servers = {@Server(url = "https://api.c930.net/"), @Server(url = "http://localhost:8080/")},
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
}
