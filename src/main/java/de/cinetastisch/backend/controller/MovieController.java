package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.MovieRequestDto;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
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

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    private final String exampleJson = "{\n  \"id\": 1,\n  \"title\": \"Guardians of the Galaxy\",\n  \"releaseYear\": \"2014\",\n  \"posterImage\": \"https://m.media-amazon.com/images/M/MV5BZTkwZjU3MTctMGExMi00YjU5LTgwMDMtOWNkZDRlZjQ4NmZhXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_SX300.jpg\",\n  \"rated\": \"PG-13\",\n  \"runtime\": \"121 min\",\n  \"genre\": \"Action, Adventure, Comedy\",\n  \"actors\": \"Chris Pratt, Vin Diesel, Bradley Cooper\",\n  \"plot\": \"A group of intergalactic criminals must pull together to stop a fanatical warrior with plans to purge the universe.\",\n  \"trailer\": \"TODO\",\n  \"imdbId\": \"tt2015381\",\n  \"imdbRating\": \"8.0\",\n  \"imdbRatingCount\": \"1,180,325\"\n}";
    private final String exampleJsonNoId = "{\n  \"title\": \"Guardians of the Galaxy\",\n  \"releaseYear\": \"2014\",\n  \"posterImage\": \"https://m.media-amazon.com/images/M/MV5BZTkwZjU3MTctMGExMi00YjU5LTgwMDMtOWNkZDRlZjQ4NmZhXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_SX300.jpg\",\n  \"rated\": \"PG-13\",\n  \"runtime\": \"121 min\",\n  \"genre\": \"Action, Adventure, Comedy\",\n  \"actors\": \"Chris Pratt, Vin Diesel, Bradley Cooper\",\n  \"plot\": \"A group of intergalactic criminals must pull together to stop a fanatical warrior with plans to purge the universe.\",\n  \"trailer\": \"TODO\",\n  \"imdbId\": \"tt2015381\",\n  \"imdbRating\": \"8.0\",\n  \"imdbRatingCount\": \"1,180,325\"\n}";

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(
            tags = {"Movies"},
            operationId = "getMovies",
            summary = "Fetch all movies",
            description = "Retrieve all movies from the database with optional query-parameters.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid inputs supplied",
                            content = @Content
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Movie>> getAll(@RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "genre", required = false) String genre,
                                              @RequestParam(value = "imdbId", required = false) String imdbId,
                                              @RequestParam(value = "rated", required = false) String rated) {
        return new ResponseEntity<>(movieService.getAllMovies(title, genre, imdbId, rated), HttpStatus.OK);
    }

    @Operation(
            tags = {"Movies"},
            operationId = "getMovie",
            summary = "Get movie by id",
            parameters = {
                    @Parameter(name = "id")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid inputs supplied",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found",
                            content = @Content
                    )
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(movieService.getMovie(id), HttpStatus.OK);
    }


    @Operation(
            tags = {"Movies"},
            operationId = "addMovie",
            summary = "Add movie by imdbId, title or your own json request body",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = MovieRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            parameters = {
                    @Parameter(name = "imdbId", example = "tt4154664"),
                    @Parameter(name = "title")
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
                            description = "Invalid inputs supplied",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found (in omdb)",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "The 409 (Conflict) status code indicates that the request could not " +
                                    "be completed due to a conflict with the current state of the target resource.",
                            content = @Content
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<Movie> addOne(@Valid @RequestBody(required = false) MovieRequestDto movie,
                                    @Valid @RequestParam(value = "imdbId", required = false) String imdbId,
                                    @Valid @RequestParam(value = "title", required = false) String title){
        return new ResponseEntity<>(movieService.addMovieByParameters(movie, imdbId, title), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Movies"},
            operationId = "replaceMovie",
            summary = "Replace a movie by id with request body",
            parameters = {
                    @Parameter(name = "id")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = MovieRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Movie.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid inputs supplied",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict, the movie you are trying to replace it with is already stored",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Movie> replaceOne(@PathVariable Long id,
                                        @RequestBody MovieRequestDto movie){
        return ResponseEntity.ok(movieService.replaceMovie(id, movie));
    }

    @Operation(
            tags = {"Movies"},
            operationId = "deleteMovie",
            summary = "Delete movie by id",
            parameters = {
                    @Parameter(name = "id")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content, successfully deleted",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input supplied",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movie found",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            tags = {"Movies"},
            operationId = "archiveMovie",
            summary = "Archive a movie by id",
            description = "It's an alternative for deleting movies"
    )
    @GetMapping("/{id}/archive")
    public ResponseEntity<Movie> archiveMovie(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.archive(id));
    }

    @Operation(
            tags = {"Movies"},
            operationId = "catalogMovie",
            summary = "Catalog a movie by id"
    )
    @GetMapping("/{id}/catalog")
    public ResponseEntity<Movie> catalogMovie(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.catalog(id));
    }


    @Operation(
            tags = {"Movies", "Screenings"},
            operationId = "getScreeningsOfMovie",
            summary = "Get Screenings of a movie",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful",
                            content = @Content(
                                    schema = @Schema(implementation = Screening.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input supplied",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Movie not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("{id}/screenings") //TODO: set timespan
    public ResponseEntity<List<Screening>> getScreenings(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.getAllScreeningsByMovie(id));
    }
}
