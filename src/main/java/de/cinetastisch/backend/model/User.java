package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "User") // Best-Practice ist die explizite Namensgebung (auch um Klassennamen zu verkürzen)
@Table(name = "user", /* Best-Practice (auch um reserved keywords zu umgehen) */
        uniqueConstraints = { /* E-Mail soll eindeutig sein (pro User eine eindeutige E-Mail-Adresse) */
                @UniqueConstraint(name = "user_email_unique", columnNames = "email") /* verkürzt den Namen des unique-identifiers von einem random String zu "user_email_unique" */
        }
    )
public class User {

    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_sequence")
    @Column(name = "id")
    private @Id Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(200)" /* Sonst MySQL-Error "BLOB/TEXT column 'email' used in key specification without a key length" */)
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "birthday", nullable = true, columnDefinition = "TEXT")
    private String birthday;

    @Column(name = "address", nullable = true, columnDefinition = "TEXT")
    private String address;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(birthday, user.birthday) && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, birthday, address);
    }
}
