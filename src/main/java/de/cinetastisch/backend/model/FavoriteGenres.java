package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.Genre;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteGenres {

    @SequenceGenerator(name = "favorite_gerne_sequence", sequenceName = "favorite_genre_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "favorite_genre_sequence")
    @Column(name = "id")
    private @Id Long id;

    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public FavoriteGenres(Genre genre, User user) {
        this.genre = genre;
        this.user = user;
    }
}
