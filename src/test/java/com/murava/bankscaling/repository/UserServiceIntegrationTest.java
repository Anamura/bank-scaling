package com.murava.bankscaling.repository;

import com.murava.bankscaling.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


@SpringBootTest
@ContextConfiguration(initializers = {UserServiceIntegrationTest.Initializer.class})
@Testcontainers
public class UserServiceIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Container
    public static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("bankscaling")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("db.sql");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgresSQLContainer.getPassword()

            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @Transactional
    public void findAllUsersGetEmailsReturned() {
        List<User> users = userDao.findAll();
        long count = userDao.count();
        String[] actualEmails = {"bgate@microsoft.com", "chric@pivotal.com", "epresley@music.com", "jlegtn@music.com"};

        assertEquals(4, count);
        assertArrayEquals(actualEmails, users.stream().map(user -> user.getEmail()).toArray());
    }

}