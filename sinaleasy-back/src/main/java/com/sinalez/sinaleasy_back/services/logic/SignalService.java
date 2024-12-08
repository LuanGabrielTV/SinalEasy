package com.sinalez.sinaleasy_back.services.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.MilestoneRecordDTO;
import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.entities.Milestone;
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
        BeanUtils.copyProperties(signalRequestDTO, signal);
        String cityIdOfSignal = signalRequestDTO.cityId();
        City cityOfSignal = cityRepository
            .findById(cityIdOfSignal)
            .orElseThrow(CityNotFoundException::new);
        signal.setCity(cityOfSignal);

        addMilestoneIfStatusChanged(signal, signalRequestDTO.status());

        return signalRepository.save(signal);
    }

    private void addMilestoneIfStatusChanged(Signal signal, int newStatus) {
        int currentStatus = signal.getStatus();

        if (currentStatus != newStatus) {
            Milestone milestone = new Milestone();
            milestone.setStatus(newStatus);
            milestone.setStatusUpdateTime(LocalDate.now());
            milestone.setSignal(signal);
            signal.setSignalMilestone(milestone);
            signal.setStatus(newStatus);
        }
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