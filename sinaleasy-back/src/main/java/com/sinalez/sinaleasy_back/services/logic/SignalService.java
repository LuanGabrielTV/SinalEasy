package com.sinalez.sinaleasy_back.services.logic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.entities.Signal;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.CityNotFoundException;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.SignalNotFoundException;
import com.sinalez.sinaleasy_back.repositories.CityRepository;
import com.sinalez.sinaleasy_back.repositories.SignalRepository;

@Service
public class SignalService {
    private final SignalRepository signalRepository;
    private final CityRepository cityRepository;

    public SignalService(SignalRepository signalRepository, CityRepository cityRepository) {
        this.signalRepository = signalRepository;
        this.cityRepository = cityRepository;
    }

    public Signal createSignal(SignalRecordDTO signalRequestDTO) {
        Signal signal = new Signal();
        BeanUtils.copyProperties(signalRequestDTO, signal);
        String cityIdOfSignal = signalRequestDTO.cityId();
        City cityOfSignal = cityRepository.findById(cityIdOfSignal)
            .orElseThrow(CityNotFoundException::new);
        signal.setCity(cityOfSignal);
        return signalRepository.save(signal);

    }

    public Signal updateSignal(SignalRecordDTO signalRequestDTO, Signal signal) {
        City cityOfSignal = cityRepository
            .findById(signalRequestDTO.cityId())
            .orElseThrow(CityNotFoundException::new);
        signal.setCity(cityOfSignal);
        signal.updateStatus(signalRequestDTO.status());
        signal.addGradeIfConcluded(
            signalRequestDTO.signalGrade().rating(),
            signalRequestDTO.signalGrade().description(),
            signalRequestDTO.signalGrade().gradeUpdateTime()
        );

        BeanUtils.copyProperties(signalRequestDTO, signal, "signalId", "signalMilestones", "city");

        return signalRepository.save(signal);
    }

    public Signal getSignalById(UUID id) {
        return signalRepository.findById(id).orElseThrow(SignalNotFoundException::new);
    }

    public List<Signal> getSigns() {
        return signalRepository.findAll();
    }

    public List<Signal> getSignsByCityId(String cityId) {
        return signalRepository.findByCityCityId(cityId);
    }

    public List<Signal> getSignsByUserId(UUID userId) {
        return signalRepository.findByUserUserId(userId);
    }

}