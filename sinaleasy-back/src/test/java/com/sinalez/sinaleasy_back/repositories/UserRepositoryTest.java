package com.sinalez.sinaleasy_back.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.sinalez.sinaleasy_back.domains.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get User by login successfully from DB")
    void shouldFindUserByLogin() {
        // Arrange
        User user = createUser("testLogin");
        persistObject(user);

        // Act
        var foundUser = userRepository.findByUserLogin("testLogin");

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testLogin");
    }

    @Test
    @DisplayName("Should not find User by non-existent login")
    void shouldNotFindUserByNonExistentLogin() {
        // Arrange
        User user = createUser("testLogin");
        persistObject(user);

        // Act
        var foundUser = userRepository.findByUserLogin("nonExistentLogin");

        // Assert
        assertThat(foundUser).isNull();
    }

    private User createUser(String userLogin) {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUserLogin(userLogin);
        user.setUserPassword("password");
        user.setUserEmail("test@example.com");
        return user;
    }

    private void persistObject(Object object) {
        if (!entityManager.contains(object)) {
            entityManager.merge(object);
        } else {
            entityManager.persist(object);
        }
        entityManager.flush();
    }
}
