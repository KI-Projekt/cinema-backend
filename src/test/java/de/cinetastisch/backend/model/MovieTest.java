package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Mock
    Movie movie = new Movie("Avengers Endgame","2019","/src/datei.png", MovieRating.PG13,"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222", MovieStatus.IN_CATALOG);


    @Test
    void getRated() {
        String exp = "PG-13";
        String act = movie.getRated();
        assertEquals(exp,act);
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
    void getTitle() {
        String exp = "Avengers Endgame";
        String act = movie.getTitle();
        assertEquals(exp,act);
    }

    @Test
    void getReleaseYear() {
        String exp = "2019";
        String act = movie.getReleaseYear();
        assertEquals(exp,act);
    }

    @Test
    void getPosterImage() {
        String exp = "/src/datei.png";
        String act = movie.getPosterImage();
        assertEquals(exp,act);
    }

    @Test
    void getRuntime() {
        String exp = "120";
        String act = movie.getRuntime();
        assertEquals(exp,act);
    }

    @Test
    void getGenre() {
        String exp = "Action";
        String act = movie.getGenre();
        assertEquals(exp,act);
    }

    @Test
    void getDirector() {
        String exp = "Anthony Russo";
        String act = movie.getDirector();
        assertEquals(exp,act);
    }

    @Test
    void getWriter() {
        String exp = "Christopher Markus";
        String act = movie.getWriter();
        assertEquals(exp,act);
    }

    @Test
    void getActors() {
        String exp = "Chris Evens";
        String act = movie.getActors();
        assertEquals(exp,act);
    }

    @Test
    void getPlot() {
        String exp = "Viel BumBum";
        String act = movie.getPlot();
        assertEquals(exp,act);
    }

    @Test
    void getTrailer() {
        String exp = "www.youtube.com/Endgame";
        String act = movie.getTrailer();
        assertEquals(exp,act);
    }

    @Test
    void getImdbId() {
        String exp = "1234IMdb";
        String act = movie.getImdbId();
        assertEquals(exp,act);
    }

    @Test
    void getImdbRating() {
        String exp = "27/10";
        String act = movie.getImdbRating();
        assertEquals(exp,act);
    }

    @Test
    void getImdbRatingCount() {
        String exp = "1222";
        String act = movie.getImdbRatingCount();
        assertEquals(exp,act);
    }

    @Test
    void getMovieStatus() {
        movie.setMovieStatus(MovieStatus.ARCHIVED);
        MovieStatus exp = MovieStatus.ARCHIVED;
        MovieStatus act = movie.getMovieStatus();
        assertEquals(exp, act);
    }

    @Test
    void setTitle() {
        String exp = "Star Wars";
        movie.setTitle("Star Wars");
        String act = movie.getTitle();
        assertEquals(exp, act);
    }

    @Test
    void setReleaseYear() {
        String exp = "Star Wars";
        movie.setTitle("Star Wars");
        String act = movie.getTitle();
        assertEquals(exp, act);
    }

    @Test
    void setPosterImage() {
        String exp = "/src/StarWars";
        movie.setPosterImage("/src/StarWars");
        String act = movie.getPosterImage();
        assertEquals(exp, act);
    }

    @Test
    void setRated() {
        String exp = "PG-13";
        movie.setRated(MovieRating.PG13);
        String act = movie.getRated();
        assertEquals(exp, act);
    }

    @Test
    void setRuntime() {
        String exp = "150";
        movie.setRuntime("150");
        String act = movie.getRuntime();
        assertEquals(exp, act);
    }

    @Test
    void setGenre() {
        String exp = "Drama";
        movie.setGenre("Drama");
        String act = movie.getGenre();
        assertEquals(exp, act);
    }

    @Test
    void setDirector() {
        String exp = "Amus Mamus";
        movie.setDirector("Amus Mamus");
        String act = movie.getDirector();
        assertEquals(exp, act);
    }

    @Test
    void setWriter() {
        String exp = "Albus Malmus";
        movie.setWriter("Albus Malmus");
        String act = movie.getWriter();
        assertEquals(exp, act);
    }

    @Test
    void setActors() {
        String exp = "Agate Spagate";
        movie.setActors("Agate Spagate");
        String act = movie.getActors();
        assertEquals(exp, act);
    }

    @Test
    void setPlot() {
        String exp = "PengPeng";
        movie.setPlot("PengPeng");
        String act = movie.getPlot();
        assertEquals(exp, act);
    }

    @Test
    void setTrailer() {
        String exp = "www.pornhub.com/Endgame";
        movie.setTrailer("www.pornhub.com/Endgame");
        String act = movie.getTrailer();
        assertEquals(exp, act);
    }

    @Test
    void setImdbId() {
        String exp = "123567imbd";
        movie.setImdbId("123567imbd");
        String act = movie.getImdbId();
        assertEquals(exp, act);
    }

    @Test
    void setImdbRating() {
        String exp = "2/100";
        movie.setImdbRating("2/100");
        String act = movie.getImdbRating();
        assertEquals(exp, act);
    }

    @Test
    void setImdbRatingCount() {
        String exp = "100";
        movie.setImdbRatingCount("100");
        String act = movie.getImdbRatingCount();
        assertEquals(exp, act);
    }

    @Test
    void setMovieStatus() {
        movie.setMovieStatus(MovieStatus.IN_CATALOG);
        MovieStatus exp = MovieStatus.IN_CATALOG;
        MovieStatus act = movie.getMovieStatus();
        assertEquals(exp, act);
    }

    @Test
    void testToString() {
        String exp = "Movie(id=null, title=Avengers Endgame, releaseYear=2019, posterImage=/src/datei.png, rated=PG-13, runtime=120, genre=Action, director=Anthony Russo, writer=Christopher Markus, actors=Chris Evens, plot=Viel BumBum, trailer=www.youtube.com/Endgame, imdbId=1234IMdb, imdbRating=27/10, imdbRatingCount=1222, movieStatus=IN_CATALOG)";
        assertEquals(exp, movie.toString());
    }
}