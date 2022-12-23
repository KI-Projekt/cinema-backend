package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data // Erstellt Getter, Setter, ToString, Equals, Hashcode ... (und mehr)
@NoArgsConstructor
@Entity(name = "User") // Best-Practice ist die explizite Namensgebung (auch um Klassennamen zu verkürzen)
@Table(
        name = "user", // Best-Practice (auch um reserved keywords zu umgehen)
        uniqueConstraints = { // E-Mail soll eindeutig sein (pro User eine eindeutige E-Mail-Adresse)
                @UniqueConstraint(name = "user_email_unique", columnNames = "email") // verkürzt den Namen des unique-identifiers von einem random String zu "user_email_unique"
        }
)
public class User {

    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "users_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "VARCHAR(200)" // Sonst MySQL-Error "BLOB/TEXT column 'email' used in key specification without a key length"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "birthday",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String birthday;

    @Column(
            name = "address",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String address;


//=== === === === === === === ===
    @OneToMany(
            cascade = {CascadeType.ALL}, // oder {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Booking> bookings = new ArrayList<>();
//=== === === === === === === ===

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void addBooking(Booking book){
        if(!this.bookings.contains(book)){
            this.bookings.add(book);
            book.setUser(this);
        }
    }
}
