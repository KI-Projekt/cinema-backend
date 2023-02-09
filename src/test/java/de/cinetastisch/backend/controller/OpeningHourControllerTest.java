package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.service.OpeningHourService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OpeningHourControllerTest {

    @InjectMocks
    OpeningHourController openingHourController;

    @Mock
    OpeningHourService openingHourService;



    @Test
    void getAll() {
        OpeningHour openingHour = new OpeningHour(DayOfWeek.FRIDAY, null, null);
        List<OpeningHour> openingHourList = List.of(openingHour,openingHour);

    }

    @Test
    void replace() {
    }
}