package de.cinetastisch.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum RoomScreenExperience {
    TWO_D("2D"),
    THREE_D("3D");

    private final String name;

    RoomScreenExperience(String dimension) {
        this.name = dimension;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static RoomScreenExperience decode(final String name) {
        return Stream.of(RoomScreenExperience.values())
                     .filter(RoomScreenExperience -> RoomScreenExperience.name.equals(name))
                     .findFirst()
                     .orElse(null);
    }

}

