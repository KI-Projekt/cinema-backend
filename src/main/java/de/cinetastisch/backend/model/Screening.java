package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.ScreeningStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.AUTO;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Screening {

    @GeneratedValue(strategy = AUTO)
    private @Id Long id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_movie_id_fk"))
    private Movie movie;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_room_id_fk"))
    private Room room;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private ScreeningStatus status = ScreeningStatus.TICKET_SALE_OPEN;

    public Screening(Movie movie, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.movie = movie;
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Screening(Movie movie, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime,
                     ScreeningStatus status) {
        this.movie = movie;
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Screening screening = (Screening) o;
        return id != null && Objects.equals(id, screening.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
