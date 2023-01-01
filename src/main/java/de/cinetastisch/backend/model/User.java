package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "User")
@Table(name = "user",
        uniqueConstraints = { /* E-Mail soll eindeutig sein (pro User eine eindeutige E-Mail-Adresse) */
                @UniqueConstraint(name = "user_email_unique", columnNames = "email") /* verkürzt den Namen des unique-identifiers von einem random String zu "user_email_unique" */
        }
    )
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Schema(accessMode = READ_ONLY)
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

    @Column(name = "country",nullable = false,columnDefinition = "TEXT")
    private String country;

    @Column(name = "city",nullable = false,columnDefinition = "TEXT")
    private String city;

    @Column(name = "zip",nullable = false,columnDefinition = "TEXT")
    private String zip;

    @Column(name = "street",nullable = false,columnDefinition = "TEXT")
    private String street;

    @Column(name = "house_number",nullable = false)
    private Integer houseNumber;

    public User(String firstName, String lastName, String email, String password, String birthday, String country, String city, String zip, String street) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && email.equals(user.email) && Objects.equals(password, user.password) && Objects.equals(birthday, user.birthday) && Objects.equals(country, user.country) && Objects.equals(city, user.city) && Objects.equals(zip, user.zip) && Objects.equals(street, user.street) && Objects.equals(houseNumber, user.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, birthday, country, city, zip, street, houseNumber);
    }
}
