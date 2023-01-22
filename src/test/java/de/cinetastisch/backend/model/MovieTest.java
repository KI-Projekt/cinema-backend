package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.MovieRating;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Mock
    Movie movie = new Movie("Avengers Endgame","2019","/src/datei.png", MovieRating.PG13,"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222");


    @Test
    void getRated() {
    }

    @Test
    void testEquals() {
        movie.setId((long) 1.2222);
        Movie testmovie1 = movie;
        Movie testmovie = new Movie();
        Boolean act = movie.equals(testmovie1);
        assertTrue(act);
        assertFalse(movie.equals(testmovie));
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
    }

    @Test
    void getTitle() {
    }

    @Test
    void getReleaseYear() {
    }

    @Test
    void getPosterImage() {
    }

    @Test
    void getRuntime() {
    }

    @Test
    void getGenre() {
    }

    @Test
    void getDirector() {
    }

    @Test
    void getWriter() {
    }

    @Test
    void getActors() {
    }

    @Test
    void getPlot() {
    }

    @Test
    void getTrailer() {
    }

    @Test
    void getImdbId() {
    }

    @Test
    void getImdbRating() {
    }

    @Test
    void getImdbRatingCount() {
    }

    @Test
    void getMovieStatus() {
    }

    @Test
    void setId() {
    }

    @Test
    void setTitle() {
    }

    @Test
    void setReleaseYear() {
    }

    @Test
    void setPosterImage() {
    }

    @Test
    void setRated() {
    }

    @Test
    void setRuntime() {
    }

    @Test
    void setGenre() {
    }

    @Test
    void setDirector() {
    }

    @Test
    void setWriter() {
    }

    @Test
    void setActors() {
    }

    @Test
    void setPlot() {
    }

    @Test
    void setTrailer() {
    }

    @Test
    void setImdbId() {
    }

    @Test
    void setImdbRating() {
    }

    @Test
    void setImdbRatingCount() {
    }

    @Test
    void setMovieStatus() {
    }

    @Test
    void testToString() {
    }
}