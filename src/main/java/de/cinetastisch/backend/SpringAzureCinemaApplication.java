package de.cinetastisch.backend;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.swagger.springdoc.SpecificationArgResolverSpringdocOperationCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Cinema API", version = "1.0.0"),
		servers = {@Server(url = "https://api.c930.net/"), @Server(url = "http://localhost:8080/")},
		tags = {
				@Tag(name = "Movies", description = "Alles über die gespeicherten Filme"),
				@Tag(name = "Rooms", description = "Alles über die Filmsäle"),
				@Tag(name = "Screenings", description = "Alles über die einzelnen Filmvorstellungen"),
				@Tag(name = "Seats"),
				@Tag(name = "Reservations"),
				@Tag(name = "Orders", description = "Weil die Order nicht nur auf den Ticketkauf spezialisiert ist, implementiert jedes eigene Produkt den Kauf"),
				@Tag(name = "Tickets"),
				@Tag(name = "Users"),
				@Tag(name = "TicketFares")
		}
)
@EnableScheduling
@EnableSpringHttpSession
public class SpringAzureCinemaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAzureCinemaApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowCredentials(true)
						.allowedOriginPatterns("*")
						.allowedHeaders("*")
						.exposedHeaders("*")
						.maxAge(3600);
			}
		};
	}

	@Bean
	public SpecificationArgResolverSpringdocOperationCustomizer specificationArgResolverSpringdocOperationCustomizer() {
		return new SpecificationArgResolverSpringdocOperationCustomizer();
	}
}
