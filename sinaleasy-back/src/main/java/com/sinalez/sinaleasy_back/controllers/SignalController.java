package com.sinalez.sinaleasy_back.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinalez.sinaleasy_back.domains.Signal;
import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.SignalDTO;
import com.sinalez.sinaleasy_back.mappers.SignalMapper;
import com.sinalez.sinaleasy_back.services.logic.SignalService;
import com.sinalez.sinaleasy_back.services.logic.UserService;
import com.sinalez.sinaleasy_back.services.logic.VotingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/signals")
@CrossOrigin
// @CrossOrigin(origins = "http://localhost:4200")
public class SignalController {
    private final SignalService signalService;
    private final SignalMapper signalMapper;
    private final UserService userService;
    private final VotingService votingService;

    public SignalController(SignalService signalService, SignalMapper signalMapper, UserService userService,
            VotingService votingService) {
        this.signalService = signalService;
        this.signalMapper = signalMapper;
        this.userService = userService;
        this.votingService = votingService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSignalById(@PathVariable(value = "id") UUID id) {
        Signal signal = signalService.getSignalById(id);
        SignalDTO signalResponseDTO = signalMapper.toDTO(signal);
        return ResponseEntity.status(HttpStatus.OK).body(signalResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<SignalDTO>> getsignals() {
        List<Signal> signals = signalService.getSignals();
        List<SignalDTO> signalsResponseDTO = signals.stream()
                .map(signalMapper::toDTO)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signalsResponseDTO);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Object> getSignalById(@PathVariable(value = "id") UUID
    // id) {
    // Signal signal = signalService.getSignalById(id);
    // return ResponseEntity.status(HttpStatus.OK).body(signal);
    // }

    @PostMapping("/")
    public ResponseEntity<SignalDTO> createSignal(
            @RequestBody @Valid SignalDTO signalRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        Signal createdSignal = signalService.createSignal(signalRequestDTO, token);
        SignalDTO signalResponseDTO = signalMapper.toDTO(createdSignal);
        return ResponseEntity.status(HttpStatus.CREATED).body(signalResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSignal

    (
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid SignalDTO signalRequestDTO) {
        Signal updatedSignal = signalService.updateSignal(signalRequestDTO, signalService.getSignalById(id));
        SignalDTO signalResponseDTO = signalMapper.toDTO(updatedSignal);
        return ResponseEntity.status(HttpStatus.OK).body(signalResponseDTO);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<List<SignalDTO>> getSignalsByCityId(@PathVariable(value = "id") String id) {
        List<Signal> signals = signalService.getSignalsByCityId(id);
        List<SignalDTO> signalsResponseDTO = new ArrayList<SignalDTO>();

        User userTester = userService.getUserById(UUID.fromString("17afce30-ff01-4766-9073-0706a141a6f6"));

        Integer votesByCityId = Integer.valueOf(votingService.countVotesByCityId(id));

        for (int i = 0; i < signals.size(); i++) {

            boolean isUserVotePresent = votingService.hasUserVoted(userTester.getUserId(),
                    signals.get(i).getSignalId());

            Integer signalVoteCount = Integer.valueOf(votingService.countVotes(signals.get(i).getSignalId()));
            BigDecimal scaleFactor = new BigDecimal(
                    10.0 + (10.0
                            * ((double) (signalVoteCount.intValue() + 1) / (double) (votesByCityId.intValue() + 1))));

            if (isUserVotePresent) {
                SignalDTO newSignalDTO = signalMapper.toDTO(signals.get(i), true, signalVoteCount,
                        scaleFactor);
                signalsResponseDTO.add(newSignalDTO);
            } else {
                SignalDTO newSignalDTO = signalMapper.toDTO(signals.get(i), false, signalVoteCount,
                        scaleFactor);
                signalsResponseDTO.add(newSignalDTO);
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(signalsResponseDTO);
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> voteSignal(@RequestBody ArrayList<String> changedVotes) {
        changedVotes.forEach(s -> votingService.voteSignal(UUID.fromString("17afce30-ff01-4766-9073-0706a141a6f6"),
                UUID.fromString(s)));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/signal/{id}")
    public ResponseEntity<List<SignalDTO>> getSignalsByUserId(@PathVariable(value = "id") UUID id) {
        List<Signal> signals = signalService.getSignalsByUserId(id);
        List<SignalDTO> signalsResponseDTO = signals.stream()
                .map(signalMapper::toDTO)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(signalsResponseDTO);

    }

}