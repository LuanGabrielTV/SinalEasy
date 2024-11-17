package com.sinalez.sinaleasy_back.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinalez.sinaleasy_back.dtos.SignalRecordDTO;
import com.sinalez.sinaleasy_back.entities.Signal;
import com.sinalez.sinaleasy_back.mappers.SignalMapper;
import com.sinalez.sinaleasy_back.services.SignalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/signs")
@CrossOrigin(origins = "http://localhost:4200")
public class SignalController {
    private final SignalService signalService;
    private final SignalMapper signalMapper;

    public SignalController(SignalService signalService, SignalMapper signalMapper) {
        this.signalService = signalService;
        this.signalMapper = signalMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSignalById(@PathVariable(value = "id") UUID id) {
        Signal signal = signalService.getSignalById(id);
        SignalRecordDTO signalDTO = signalMapper.toDTO(signal);
        return ResponseEntity.status(HttpStatus.OK).body(signalDTO);
    }
    
    // @GetMapping("/{id}")
    // public ResponseEntity<Object> getSignalById(@PathVariable(value = "id") UUID id) {
    //     Signal signal = signalService.getSignalById(id);
    //     return ResponseEntity.status(HttpStatus.OK).body(signal);
    // }

    @PostMapping("/")
    public ResponseEntity<Signal> createSignal(@RequestBody @Valid SignalRecordDTO signalRecordDTO) {
        Signal createdSignal = signalService.createSignal(signalRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSignal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSignal
    
    (
        @PathVariable(value = "id") UUID id, 
        @RequestBody @Valid SignalRecordDTO signalRecordDTO
    ) {
        Signal updatedSignal = signalService.updateSignal(signalRecordDTO, signalService.getSignalById(id));
        return ResponseEntity.status(HttpStatus.OK).body(updatedSignal);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<List<Signal>> getSignsByCityId(@PathVariable(value = "id") String id) {
    //     List<Signal> signs = signalService.getSignsByCityId(id);

    //     return ResponseEntity.status(HttpStatus.OK).body(signs);
    // }

    @GetMapping("/city/{id}")
    public ResponseEntity<List<SignalRecordDTO>> getSignsByCityId(@PathVariable(value = "id") String id) {
        List<Signal> signals = signalService.getSignsByCityId(id);
        List<SignalRecordDTO> signalDTOs = signals.stream().map(signalMapper::toDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(signalDTOs);
    }

}