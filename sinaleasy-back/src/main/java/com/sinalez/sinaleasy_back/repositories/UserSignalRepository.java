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

    UserSignal findByUserUserIdAndSignalSignalId(UUID userId, UUID signalId); // Verifica se existe um registro na tabela UserSignal para um usuário (userId) e um sinal (signalId). sera utilizada para verificacao, evitando duplicação de votos.

    // @Query(
    //     value = "SELECT COUNT(DISTINCT us.user_id, us.signal_id) " +
    //             "FROM tb_users_signals us " +
    //             "INNER JOIN tb_users u ON us.user_id = u.user_id " +
    //             "INNER JOIN tb_signals s ON us.signal_id = s.signal_id", 
    //     nativeQuery = true
    // )
    // long countDistinctUserSignalCombinations();

    @Query(value="select count(signal_id) from tb_users_signals where signal_id in (select signal_id from tb_signals where city_id=?1);", nativeQuery = true)
    Integer countByCity(String cityId);
}
