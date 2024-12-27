package com.sinalez.sinaleasy_back.services.logic;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinalez.sinaleasy_back.dtos.VoteRecordDTO;
import com.sinalez.sinaleasy_back.entities.Signal;
import com.sinalez.sinaleasy_back.entities.User;
import com.sinalez.sinaleasy_back.entities.UserSignal;
import com.sinalez.sinaleasy_back.repositories.UserSignalRepository;

@Service
public class VotingService {
    public final UserSignalRepository userSignalRepository;
    public final SignalService signalService;
    public final UserService userService;
    public VotingService(
            UserSignalRepository userSignalRepository,
            SignalService signalService,
            UserService userService
        ) {
        this.userSignalRepository = userSignalRepository;
        this.signalService = signalService;
        this.userService = userService;
    }

    // Em vez de reportar o repositorio, vou importar o servico

    @Transactional
    public void voteSignal(VoteRecordDTO voteRequestDTO, UUID userId) {
        User user = userService.getUserById(userId);
        Signal signal = signalService.getSignalById(voteRequestDTO.signalId());

        // verifica se o usuario ja votou nesse sinal
        UserSignal existingUserSignal = userSignalRepository.existsByUserUserIdAndSignalSignalId(userId, signal.getSignalId());

        if (existingUserSignal != null) {
            // se o voto ja existir, removemos ele (desfazer o voto)
            userSignalRepository.deleteByUserUserIdAndSignalSignalId(userId, signal.getSignalId());
        } else {
            // se o voto n existir, criamos um novo
            UserSignal userSignal = new UserSignal();
            userSignal.setUser(user);
            userSignal.setSignal(signal);
            userSignal.setVoted(true);
            userSignalRepository.save(userSignal);
        }
        
    }
    
    public boolean hasUserVoted(UUID userId, UUID signalId) {
        // Verifica se existe um registro no repositório para o usuário e o sinal
        return userSignalRepository.existsByUserUserIdAndSignalSignalId(userId, signalId) != null;
    }
    

    // Metodo para contar o numero de votos de um sinal
    public Integer countVotes(UUID signalId) {
        return userSignalRepository.countBySignal_signalId(signalId);
    }
 
}