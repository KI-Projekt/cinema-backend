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
public class OpeningHour {
    @Id
    @GeneratedValue
    public int openingHour_id;
    public String weekday;
    public Time openingtime;
    public Time closingtime;

    protected OpeningHour() {}

    public OpeningHour(int openingHour_id, String day, Time begin, Time end) {
        this.openingHour_id = openingHour_id;
        this.weekday = day;
        this.openingtime = begin;
        this.closingtime = end;
    }


}
