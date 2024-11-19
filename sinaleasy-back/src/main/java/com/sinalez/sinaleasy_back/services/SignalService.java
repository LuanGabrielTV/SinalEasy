package com.sinalez.sinaleasy_back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.entities.Signal;
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
        // inserir slug?
    }

    public Signal createSignal(SignalRecordDTO signalRecordDTO) {
        Signal signal = new Signal();
        BeanUtils.copyProperties(signalRecordDTO, signal);
        String cityIdOfSignal = signalRecordDTO.cityId();
        City cityOfSignal = cityRepository.findById(cityIdOfSignal)
            .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
        signal.setCity(cityOfSignal);
        return signalRepository.save(signal);

    }

    public Signal updateSignal(SignalRecordDTO signalRecordDTO, Signal signal) {
        BeanUtils.copyProperties(signalRecordDTO, signal);
        return signalRepository.save(signal);
        // não pode mudar cidade em um sinal já existente
        // signal não pode ser removido enquanto houver qualquer curtida
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

}