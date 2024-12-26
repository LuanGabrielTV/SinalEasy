package com.sinalez.sinaleasy_back.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sinalez.sinaleasy_back.entities.UserSignal;

@Repository
public interface UserSignalRepository extends JpaRepository<UserSignal, UUID>{

    // Integer countBySignalId(UUID signalId); // Conta o número de registros na tabela UserSignal associados a um determinado signalId. Calcula o total de likes/dislikes de um sinal (calcular o numberOfLikes)

    Integer countBySignal_signalId(UUID signalId);

    void deleteByUserUserIdAndSignalSignalId(UUID userId, UUID signalId); // Deleta um registro da tabela UserSignal com base no userId e signalId (remove o voto)

    UserSignal existsByUserUserIdAndSignalSignalId(UUID userId, UUID signalId); // Verifica se existe um registro na tabela UserSignal para um usuário (userId) e um sinal (signalId). sera utilizada para verificacao, evitando duplicação de votos.

    @Query(
        value = "SELECT COUNT(DISTINCT su.user_id, su.signal_id) " +
                "FROM signal_usuario su " +
                "INNER JOIN usuario u ON su.user_id = u.user_id " +
                "INNER JOIN signal s ON su.signal_id = s.signal_id", 
        nativeQuery = true
    )
    long countDistinctUserSignalCombinations();
}
