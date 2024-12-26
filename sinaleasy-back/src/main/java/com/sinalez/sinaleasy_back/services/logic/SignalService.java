package com.sinalez.sinaleasy_back.services.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.GradeRecordDTO;
import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.entities.Grade;
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
        Milestone milestone = new Milestone();
        milestone.setStatus(0);
        milestone.setStatusUpdateTime(LocalDate.now());
        milestone.setSignal(signal);
        signal.setSignalMilestone(milestone);
        return signalRepository.save(signal);

    }

    public Signal updateSignal(SignalRecordDTO signalRequestDTO, Signal signal) {
        String cityIdOfNewSignal = signalRequestDTO.cityId();
        City cityOfSignal = cityRepository
            .findById(cityIdOfNewSignal)
            .orElseThrow(CityNotFoundException::new);
        signal.setCity(cityOfSignal);
        addMilestoneIfStatusChanged(signal, signalRequestDTO.status());
        addGradeIfSignalConcluded(signal, signalRequestDTO.signalGrade());

        // BeanUtils.copyProperties(signalRequestDTO, signal);
        BeanUtils.copyProperties(signalRequestDTO, signal, "signalId", "signalMilestones", "city");
        
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

    private void addGradeIfSignalConcluded(Signal signal, GradeRecordDTO gradeRecordDTO){
        int currentStatus = signal.getStatus();
        if(currentStatus == 3){
            Grade grade = new Grade();
            grade.setRating(gradeRecordDTO.rating());
            grade.setDescription(gradeRecordDTO.description());
            grade.setDate(gradeRecordDTO.gradeUpdateTime());
            grade.setSignal(signal);
            signal.setGrade(grade);
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