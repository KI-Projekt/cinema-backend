package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.MovieRequestDto;
import de.cinetastisch.backend.dto.response.MovieResponseDto;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
    public ResponseEntity<List<MovieResponseDto>> getAll(@RequestParam(value = "title", required = false) String title,
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
    public ResponseEntity<MovieResponseDto> getOne(@PathVariable("id") Long id){
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
    public ResponseEntity<MovieResponseDto> addOne(@Valid @RequestBody(required = false) MovieRequestDto movie,
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
    public ResponseEntity<MovieResponseDto> replaceOne(@PathVariable Long id,
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
    @PutMapping("/{id}/archive")
    public ResponseEntity<MovieResponseDto> archiveMovie(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.archive(id));
    }

    @Operation(
            tags = {"Movies"},
            operationId = "catalogMovie",
            summary = "Catalog a movie by id"
    )
    @PutMapping("/{id}/catalog")
    public ResponseEntity<MovieResponseDto> catalogMovie(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.catalog(id));
    }
}
