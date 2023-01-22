package de.cinetastisch.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table
public class OpeningHours {
    @Id
    @GeneratedValue
    public int openingHours_id;
    public String day;
    public Time begin;
    public Time end;

    protected OpeningHours() {}

    public OpeningHours(int openingHours_id, String day, Time begin, Time end) {
        this.openingHours_id = openingHours_id;
        this.day = day;
        this.begin = begin;
        this.end = end;
    }


}
