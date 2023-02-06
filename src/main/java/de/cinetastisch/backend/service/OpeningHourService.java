package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.repository.OpeningHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeningHourService {
    @Autowired
    OpeningHourRepository openingHourRepository;
    public List<OpeningHour> getAllOpeningHours(){
        return openingHourRepository.findAll();
    }

    public OpeningHour replaceOpeningHour(Long id, OpeningHour openingHour){
        OpeningHour oldOpeningHour = openingHourRepository.getReferenceById(id);
        oldOpeningHour.setOpeningtime(openingHour.openingtime);
        oldOpeningHour.setClosingtime(openingHour.closingtime);
        openingHourRepository.save(oldOpeningHour);
        return oldOpeningHour;
    }
}
