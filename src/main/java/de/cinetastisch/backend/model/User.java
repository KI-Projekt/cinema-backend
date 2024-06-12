package de.cinetastisch.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "user",
        uniqueConstraints = { /* E-Mail soll eindeutig sein (pro User eine eindeutige E-Mail-Adresse) */
                @UniqueConstraint(name = "user_email_unique", columnNames = "email") /* verkürzt den Namen des unique-identifiers von einem random String zu "user_email_unique" */
        }
    )
public class User {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_sequence")
    @Column(name = "id")
    private @Id Long id;
    private String firstName;
    private String lastName;
    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(200)" /* Sonst MySQL-Error "BLOB/TEXT column 'email' used in key specification without a key length" */)
    private String email;
    private String password;
    private LocalDate birthday;
    private String country;
    private String city;
    private String zip;
    private String street;
    private Integer houseNumber;
    private String role = "ROLE_USER";
    private boolean firstLogin = true;
    private boolean aiAccepted = false;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;




    public User(String firstName, String lastName, String email, String password, LocalDate birthday, String country,
                String city, String zip, String street, Integer houseNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder().append(id, user.id).append(firstName,
                                                              user.firstName).append(
                lastName, user.lastName).append(email, user.email).append(password, user.password).append(birthday,
                                                                                                          user.birthday).append(
                country, user.country).append(city, user.city).append(zip, user.zip).append(street, user.street).append(
                houseNumber, user.houseNumber).append(role, user.role).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(firstName).append(lastName).append(email).append(
                password).append(birthday).append(country).append(city).append(zip).append(street).append(
                houseNumber).append(role).toHashCode();
    }


}
