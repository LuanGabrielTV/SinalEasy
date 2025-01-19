package com.sinalez.sinaleasy_back.services.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.domains.Milestone;
import com.sinalez.sinaleasy_back.domains.Signal;
import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.SignalDTO;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.CityNotFoundException;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.SignalNotFoundException;
import com.sinalez.sinaleasy_back.repositories.CityRepository;
import com.sinalez.sinaleasy_back.repositories.UserRepository;
import com.sinalez.sinaleasy_back.repositories.SignalRepository;

@Service
public class SignalService {
    private final SignalRepository signalRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public SignalService(SignalRepository signalRepository, CityRepository cityRepository,
            UserRepository userRepository) {
        this.signalRepository = signalRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    public Signal createSignal(SignalDTO signalRequestDTO) {
        Signal signal = new Signal();
        BeanUtils.copyProperties(signalRequestDTO, signal);
        String cityIdOfSignal = signalRequestDTO.cityId();
        City cityOfSignal = cityRepository.findById(cityIdOfSignal)
                .orElseThrow(CityNotFoundException::new);
        signal.setCity(cityOfSignal);

        String userIdOfSignal = signalRequestDTO.userId();
        User userOfSignal = userRepository.findById(UUID.fromString(userIdOfSignal))
                .orElseThrow(CityNotFoundException::new);
        signal.setUser(userOfSignal);

        Milestone milestone = new Milestone();
        milestone.setStatus(0);
        milestone.setStatusUpdateTime(LocalDate.now());
        milestone.setSignal(signal);
        signal.setSignalMilestone(milestone);
        return signalRepository.save(signal);

    }

    public Signal updateSignal(SignalDTO signalRequestDTO, Signal signal) {
        City cityOfSignal = cityRepository
                .findById(signalRequestDTO.cityId())
                .orElseThrow(CityNotFoundException::new);
        signal.setCity(cityOfSignal);
        signal.updateStatus(signalRequestDTO.status());
        if (signalRequestDTO.status() == 3) {
            signal.addGradeIfConcluded(
                    signalRequestDTO.signalGrade().rating(),
                    signalRequestDTO.signalGrade().description(),
                    signalRequestDTO.signalGrade().gradeUpdateTime());
        }

        BeanUtils.copyProperties(signalRequestDTO, signal, "signalId", "signalMilestones", "city");

        return signalRepository.save(signal);
    }

    public Signal getSignalById(UUID id) {
        return signalRepository.findById(id).orElseThrow(SignalNotFoundException::new);
    }

    public List<Signal> getSignals() {
        return signalRepository.findAll();
    }

    public List<Signal> getSignalsByCityId(String cityId) {
        return signalRepository.findByCityCityId(cityId);
    }

    public List<Signal> getSignalsByUserId(UUID userId) {
        return signalRepository.findByUserUserId(userId);
    }

}