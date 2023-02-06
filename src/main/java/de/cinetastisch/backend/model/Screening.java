package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.ScreeningStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@SQLDelete(sql = "UPDATE screening SET status = 'CANCELLED' WHERE id=?")

public class Screening {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "screening_sequence", sequenceName = "screening_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "screening_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_movie_id_fk"))
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_room_id_fk"))
    private Room room;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private boolean isThreeD = false;
    private boolean isDolbyAtmos = false;

    @Enumerated(EnumType.STRING)
    private ScreeningStatus status = ScreeningStatus.TICKET_SALE_OPEN;

    public Screening(Movie movie, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean isThreeD,
                     boolean isDolbyAtmos, ScreeningStatus status) {
        this.movie = movie;
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isThreeD = isThreeD;
        this.isDolbyAtmos = isDolbyAtmos;
        this.status = status;
    }

    public Screening(Long id, Movie movie, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime,
                     boolean isThreeD, boolean isDolbyAtmos, ScreeningStatus status) {
        this.id = id;
        this.movie = movie;
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isThreeD = isThreeD;
        this.isDolbyAtmos = isDolbyAtmos;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return id.equals(screening.id) && Objects.equals(movie, screening.movie) && Objects.equals(room,screening.room) && Objects.equals(startDateTime, screening.startDateTime) && Objects.equals(endDateTime,screening.endDateTime) && status == screening.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, room, startDateTime, endDateTime, status);
    }

    @PrePersist
    @PostLoad
    public void updateStatus(){
        if(this.status == ScreeningStatus.TICKET_SALE_OPEN && this.endDateTime.isBefore(LocalDateTime.now())){
            this.status = ScreeningStatus.TICKET_SALE_CLOSED;
        }
    }
}
