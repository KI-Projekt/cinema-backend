package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.pojo.MovieRequest;
import de.cinetastisch.backend.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    private final String exampleJson = "{\n  \"id\": 1,\n  \"title\": \"Guardians of the Galaxy\",\n  \"releaseYear\": \"2014\",\n  \"posterImage\": \"https://m.media-amazon.com/images/M/MV5BZTkwZjU3MTctMGExMi00YjU5LTgwMDMtOWNkZDRlZjQ4NmZhXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_SX300.jpg\",\n  \"rated\": \"PG-13\",\n  \"runtime\": \"121 min\",\n  \"genre\": \"Action, Adventure, Comedy\",\n  \"actors\": \"Chris Pratt, Vin Diesel, Bradley Cooper\",\n  \"plot\": \"A group of intergalactic criminals must pull together to stop a fanatical warrior with plans to purge the universe.\",\n  \"trailer\": \"TODO\",\n  \"imdbId\": \"tt2015381\",\n  \"imdbRating\": \"8.0\",\n  \"imdbRatingCount\": \"1,180,325\"\n}";

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(
            tags = {"Movies"},
            operationId = "getMovies",
            summary = "Fetch all movies",
            description = "Retrieve all movies from the database with optional query-parameters.",
            parameters = {
                    @Parameter(
                            name = "title",
                            description = "Query movies by title",
                            example = "Galaxy"
                    ),
                    @Parameter(
                            name = "genre",
                            description = "Lists all movies with a specific genre",
                            example = "Action"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(name = "Successful Example", value = "[\n"+exampleJson+"\n]")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input supplied",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    )
            }
    )

    public ResponseEntity<List<Movie>> getAll(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "genre", required = false) String g) {
        List<Movie> response = new ArrayList<>();

        if(title != null && !title.isBlank()){
            response.addAll(movieService.getAllMoviesByTitle(title));
        } else if (g != null && !g.isBlank()){
            response.addAll(movieService.getAllMoviesByGenre(g));
        } else {
            response.addAll(movieService.getAllMovies());
        }

        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = {"Movies"},
            operationId = "getMovie",
            summary = "Get movie by id",
            parameters = {
                    @Parameter(
                            name = "id",
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(name = "Successful Example", value = exampleJson)
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input supplied",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getOne(@PathVariable("id") Long id){
        return movieService.getMovie(id);
    }

    @Operation(
            tags = {"Movies"},
            operationId = "addMovie",
            summary = "Add movie by imdbId, title or your own json request body",
            parameters = {
                    @Parameter(
                            name = "imdbId",
                            example = "tt4154664"
                    ),
                    @Parameter(
                            name = "title",
                            example = "Captain Marvel"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input supplied",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found (in omdb)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping()                // POST http://localhost:8080/api/movies/add?title=Guardians of the Galaxy
    public ResponseEntity<Movie> addOne(@Valid @RequestBody(required = false) MovieRequest movieRequest,
                                        @Valid @RequestParam(value = "imdbId", required = false) String imdbId,
                                        @Valid @RequestParam(value = "title", required = false) String movieTitle){
        if (movieRequest != null){
            movieService.addMovieDto(movieRequest);
        }

        Movie newMovie;
        Movie existingMovie;

        if (imdbId != null && !imdbId.isBlank()) {      // If imdbID is given

            try {
                existingMovie = movieService.getMovieByImdbId(imdbId);
            } catch (ResourceNotFoundException ignore){
                newMovie = movieService.getOmdbMovieByImdbId(imdbId);
                return new ResponseEntity<>(movieService.addMovie(newMovie), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(existingMovie, HttpStatus.CONFLICT);



        } else if (movieTitle != null && !movieTitle.isBlank()) {   // If only movieTitle is given
            movieTitle = movieTitle.replace("\"", "");

            try {
                existingMovie = movieService.getMovieByTitle(movieTitle); // Dieser Check ist zu streng, TODO
            } catch (ResourceNotFoundException ignore){
                newMovie = movieService.getOmdbMovieByTitle(movieTitle);

                try {
                    existingMovie = movieService.getMovieByTitle(newMovie.getTitle());
                } catch (ResourceNotFoundException ignore2){
                    return new ResponseEntity<>(movieService.addMovie(newMovie), HttpStatus.CREATED);
                }
                return new ResponseEntity<>(existingMovie, HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(existingMovie, HttpStatus.CONFLICT);
            // The 409 (Conflict) status code indicates that the request could not be completed
            // due to a conflict with the current state of the target resource.
        }
        return ResponseEntity.badRequest().build(); // Else none given
    }

    @Operation(
            tags = {"Movies"},
            operationId = "replaceMovie",
            summary = "Replace a movie by id with request body",
            parameters = {
                    @Parameter(
                            name = "id",
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(name = "Successful Example", value = exampleJson)
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input supplied",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> replaceOne(@PathVariable Long id, @RequestBody Movie movie){
        return ResponseEntity.ok(movieService.replaceMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/screenings") //TODO: set timespan
    public ResponseEntity<List<Screening>> getScreenings(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.getAllScreeningsByMovie(id));
    }
}
