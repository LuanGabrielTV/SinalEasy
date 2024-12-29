package com.sinalez.sinaleasy_back.repositories;


import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.entities.Signal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class SignalRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SignalRepository signalRepository;

    @Test
    @DisplayName("Should get Signal successfully from DB")
    void shouldFindSignalByName() {
        // Arrange
        City city = new City();
        city.setCityId("1234");
        city.setName("Name City");
        entityManager.persist(city);

        Signal signal = new Signal();
        signal.setName("Name Signal");
        signal.setDescription("Test Description");
        signal.setAddress("Test Address");
        signal.setLatitude(12.345);
        signal.setLongitude(67.890);
        signal.setTypeOfSignal(1);
        signal.setStatus(1);
        signal.setCity(city);
        entityManager.persist(signal);
        entityManager.flush();

        // Act
        Optional<Signal> foundSignal = signalRepository.findByName("Test Signal");

        // Assert
        assertThat(foundSignal).isPresent();
        assertThat(foundSignal.get().getName()).isEqualTo("Test Signal");
        assertThat(foundSignal.get().getCity().getName()).isEqualTo("Test City");
    }
    
}
