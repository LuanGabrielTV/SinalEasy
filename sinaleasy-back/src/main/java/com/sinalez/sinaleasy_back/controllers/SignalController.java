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
import com.sinalez.sinaleasy_back.entities.User;
import com.sinalez.sinaleasy_back.mappers.SignalMapper;
import com.sinalez.sinaleasy_back.services.logic.SignalService;
import com.sinalez.sinaleasy_back.services.logic.UserService;
import com.sinalez.sinaleasy_back.services.logic.VotingService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/signals")
// @CrossOrigin
@CrossOrigin(origins = "http://localhost:4200")
public class SignalController {
    private final SignalService signalService;
    private final SignalMapper signalMapper;
    private final UserService userService;
    private final VotingService votingService;

    public SignalController(SignalService signalService, SignalMapper signalMapper, UserService userService, VotingService votingService) {
        this.signalService = signalService;
        this.signalMapper = signalMapper;
        this.userService = userService;
        this.votingService = votingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSignalById(@PathVariable(value = "id") UUID id) {
        Signal signal = signalService.getSignalById(id);
        SignalRecordDTO signalResponseDTO = signalMapper.toDTO(signal);
        return ResponseEntity.status(HttpStatus.OK).body(signalResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<SignalRecordDTO>> getsignals() {
        List<Signal> signals = signalService.getSignals();
        List<SignalRecordDTO> signalsResponseDTO = signals.stream()
            .map(signalMapper::toDTO)
            .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signalsResponseDTO);
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
    public ResponseEntity<List<SignalRecordDTO>> getSignalsByCityId(@PathVariable(value = "id") String id) {
        List<Signal> signals = signalService.getSignalsByCityId(id);
        List<SignalRecordDTO> signalsResponseDTO = signals.stream()
            .map(signalMapper::toDTO)
            .toList();
            
        
        // o user logado, por enquanto, é o userTester
        User userTester = userService.getUserById(UUID.fromString("17afce30-ff01-4766-9073-0706a141a6f6"));


        for (int i = 0; i < signalsResponseDTO.size(); i++) {
            int signalVoteCount = votingService.countVotes(signals.get(i).getSignalId());
            signalsResponseDTO.get(i).setNumberOfLikes(signalVoteCount);
         
            boolean isUserVotePresent = votingService.hasUserVoted(userTester.getUserId(), signals.get(i).getSignalId());

            if(isUserVotePresent){
                // votingService.voteSignal(userTester.getUserId(), signals.get(i).getSignalId());
                signalsResponseDTO.get(i).setLiked(true);
                
            };
            
        }

        return ResponseEntity.status(HttpStatus.OK).body(signalsResponseDTO);
    }

    @PostMapping("/{signalId}/vote")
    public ResponseEntity<Void> voteSignal(
        @PathVariable(value = "signalId") UUID signalId) {
            votingService.voteSignal(UUID.fromString("17afce30-ff01-4766-9073-0706a141a6f6"), signalId);
            return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/signal/{id}")
    public ResponseEntity<List<SignalRecordDTO>> getSignalsByUserId(@PathVariable(value = "id") UUID id) {
        List<Signal> signals = signalService.getSignalsByUserId(id);
        List<SignalRecordDTO> signalsResponseDTO = signals.stream()
            .map(signalMapper::toDTO)
            .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signalsResponseDTO);

    }



}