package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.repository.OpeningHourRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningHourService {

    OpeningHourRepository openingHourRepository;

    public OpeningHourService(OpeningHourRepository openingHourRepository) {
        this.openingHourRepository = openingHourRepository;
    }

    public List<OpeningHour> getAllOpeningHours(){
        return openingHourRepository.findAll();
    }

    public OpeningHour replaceOpeningHour(Long id, OpeningHour openingHour){
        OpeningHour oldOpeningHour = openingHourRepository.getReferenceById(id);
        oldOpeningHour.setId(id);
        oldOpeningHour.setOpeningtime(openingHour.openingtime);
        oldOpeningHour.setClosingtime(openingHour.closingtime);
        return openingHourRepository.save(oldOpeningHour);
    }
}
