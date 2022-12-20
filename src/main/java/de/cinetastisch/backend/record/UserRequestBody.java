package de.cinetastisch.backend.record;

public class UserRequestBody {
    public record newUser(
        String firstName,
        String lastName,
        String email,
        String password
    ){}
}
