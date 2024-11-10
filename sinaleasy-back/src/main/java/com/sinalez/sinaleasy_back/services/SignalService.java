package com.sinalez.sinaleasy_back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.Signal;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.SignalNotFoundException;
import com.sinalez.sinaleasy_back.repositories.SignalRepository;

@Service
public class SignalService {
    private final SignalRepository signalRepository;

    public SignalService(SignalRepository signalRepository) {
        this.signalRepository = signalRepository;
        // inserir slug?
    }

    public Signal createSignal(SignalRecordDTO signalRecordDTO) {
        Signal signal = new Signal();
        BeanUtils.copyProperties(signalRecordDTO, signal);
        return signalRepository.save(signal);

    }

    public Signal updateSignal(SignalRecordDTO signalRecordDTO, Signal signal) {
        BeanUtils.copyProperties(signalRecordDTO, signal);
        return signalRepository.save(signal);
    }

    public Signal getSignalById(UUID id) {
        return signalRepository.findById(id).orElseThrow(SignalNotFoundException::new);
    }

    public List<Signal> getSignsByCityId(long id) {
        return signalRepository.findByCityId(0);
    }


}
