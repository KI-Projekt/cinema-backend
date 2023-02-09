package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.OpeningHourRequestDto;
import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.repository.OpeningHourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class OpeningHourService {


    OpeningHourRepository openingHourRepository;

    public List<OpeningHour> getAllOpeningHours(){
        return openingHourRepository.findAll();
    }

    public OpeningHour replaceOpeningHour(Long id, OpeningHourRequestDto openingHour){
        OpeningHour oldOpeningHour = openingHourRepository.getReferenceById(id);
        oldOpeningHour.setOpeningtime(LocalTime.parse(openingHour.openingTime()));
        oldOpeningHour.setClosingtime(LocalTime.parse(openingHour.closingTime()));
        return openingHourRepository.save(oldOpeningHour);
    }
}
