package com.sinalez.sinaleasy_back.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.domains.Signal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class SignalRepositoryTest {

    // posteriormente, criar testes para achar sinais atrelados ao usuario
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SignalRepository signalRepository;

    @Test
    @DisplayName("Should get Signal successfully from DB")
    void shouldFindSignalByName() {
        // Arrange
        Signal signal = createSignal("Existent name", "123");
        persistObjects(signal);

        // Act
        Optional<Signal> foundSignal = signalRepository.findByName("Existent name");
    
        // Assert
        assertThat(foundSignal).isPresent();
        assertThat(foundSignal.get().getName()).isEqualTo("Existent name");
    }

    @Test
    @DisplayName("Should not get Signal successfully from DB")
    void shouldNotFindSignalByName() {
        // Arrange
        Signal signal = createSignal("Existent name", "123");
        persistObjects(signal);
        // Act: buscando o sinal nao existente
        Optional<Signal> foundSignal = signalRepository.findByName("Nonexistent Signal");

        // Assert
        assertThat(foundSignal).isEmpty();
    }

    @Test
    @DisplayName("Should get Signal by CityId successfully from DB")
    void shouldFindSignalByCityId() {
        // Arrange

        City city1 = createCity("123");
        City city2 = createCity("000");

        Signal signal1 = createSignal("Signal1");
        signal1.setCity(city1);

        Signal signal2 = createSignal("signal2");
        signal2.setCity(city1);

        Signal signal3 = createSignal("signal3");
        signal3.setCity(city2);

        persistObjects(signal1, signal2, signal3);

        // Act
        List<Signal> foundSignals = signalRepository.findByCityCityId("123");

        // Assert
        assertThat(foundSignals).hasSize(2);
        assertThat(foundSignals).extracting(Signal::getName).containsExactlyInAnyOrder("Signal1", "signal2"); // deve conter o signal 1 e 2

    }

    private void persistObjects(Object... objects) {
        for (Object object : objects) {
            entityManager.persist(object);
        }
        entityManager.flush();
    }

    private City createCity(String cityId){
        City city = new City();
        city.setCityId(cityId);
        city.setName("Test City");
        entityManager.persist(city);
        entityManager.flush();
        return city;
    }

    private Signal createSignal(String name, String cityId) {
        Signal signal = new Signal();
        signal.setName(name);
        signal.setDescription("Test Description");
        signal.setAddress("Test Address");
        signal.setLatitude(12.345);
        signal.setLongitude(67.890);
        signal.setTypeOfSignal(1);
        signal.setStatus(1);
        signal.setCity(createCity("132"));
        return signal;
    }


    private Signal createSignal(String name) {
        Signal signal = new Signal();
        signal.setName(name);
        signal.setDescription("Test Description");
        signal.setAddress("Test Address");
        signal.setLatitude(12.345);
        signal.setLongitude(67.890);
        signal.setTypeOfSignal(1);
        signal.setStatus(1);
        return signal;
    }
}
