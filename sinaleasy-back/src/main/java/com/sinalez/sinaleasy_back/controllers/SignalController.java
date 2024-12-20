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
import com.sinalez.sinaleasy_back.services.logic.SignalService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/signs")
// @CrossOrigin
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
        SignalRecordDTO signalResponseDTO = signalMapper.toDTO(signal);
        return ResponseEntity.status(HttpStatus.OK).body(signalResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<SignalRecordDTO>> getSigns() {
        List<Signal> signs = signalService.getSigns();
        List<SignalRecordDTO> signsResponseDTO = signs.stream()
            .map(signalMapper::toDTO)
            .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signsResponseDTO);
    }
    
    // @GetMapping("/{id}")
    // public ResponseEntity<Object> getSignalById(@PathVariable(value = "id") UUID id) {
    //     Signal signal = signalService.getSignalById(id);
    //     return ResponseEntity.status(HttpStatus.OK).body(signal);
    // }

    @PostMapping("/")
    public ResponseEntity<SignalRecordDTO> createSignal(@RequestBody @Valid SignalRecordDTO signalRequestDTO) {
        Signal createdSignal = signalService.createSignal(signalRequestDTO);
        SignalRecordDTO signalResponseDTO = signalMapper.toDTO(createdSignal);
        return ResponseEntity.status(HttpStatus.CREATED).body(signalResponseDTO);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSignal
    
    (
        @PathVariable(value = "id") UUID id, 
        @RequestBody @Valid SignalRecordDTO signalRequestDTO
    ) {
        Signal updatedSignal = signalService.updateSignal(signalRequestDTO, signalService.getSignalById(id));
        SignalRecordDTO signalResponseDTO = signalMapper.toDTO(updatedSignal);
        return ResponseEntity.status(HttpStatus.OK).body(signalResponseDTO);
    }


    @GetMapping("/city/{id}")
    public ResponseEntity<List<SignalRecordDTO>> getSignsByCityId(@PathVariable(value = "id") String id) {
        List<Signal> signs = signalService.getSignsByCityId(id);
        List<SignalRecordDTO> signsResponseDTO = signs.stream()
            .map(signalMapper::toDTO)
            .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signsResponseDTO);
    }

    @GetMapping("/signal/{id}")
    public ResponseEntity<List<SignalRecordDTO>> getSignsByUserId(@PathVariable(value = "id") UUID id) {
        List<Signal> signs = signalService.getSignsByUserId(id);
        List<SignalRecordDTO> signsResponseDTO = signs.stream()
            .map(signalMapper::toDTO)
            .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signsResponseDTO);

    }

}