package com.sinalez.sinaleasy_back.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.sinalez.sinaleasy_back.domains.City;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@ActiveProfiles("test")
public class CityRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    @DisplayName("Should get City by name successfully from DB")
    void shouldFindCityByName() {
        // Arrange
        City city = createCity("Test City");
        persistObject(city);

        // Act
        Optional<City> foundCity = cityRepository.findByName("Test City");

        // Assert
        assertThat(foundCity).isPresent();
        assertThat(foundCity.get().getName()).isEqualTo("Test City");
    }

    @Test
    @DisplayName("Should not find City by non-existent name")
    void shouldNotFindCityByNonExistentName() {
        // Arrange
        City city = createCity("Test City");
        persistObject(city);

        // Act
        Optional<City> foundCity = cityRepository.findByName("NonExistent City");

        // Assert
        assertThat(foundCity).isEmpty();
    }

    private City createCity(String name) {
        City city = new City();
        city.setCityId("123");
        city.setName(name);
        return city;
    }

    private void persistObject(Object object) {
        entityManager.persist(object);
        entityManager.flush();
    }
}
