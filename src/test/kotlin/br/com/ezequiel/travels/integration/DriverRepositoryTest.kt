package br.com.ezequiel.travels.integration

import br.com.ezequiel.travels.ApplicationContextTest
import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.time.LocalDate

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DriverRepositoryTest : ApplicationContextTest() {

    @Autowired
    private lateinit var subject: DriverRepository

    @BeforeAll
    fun setup() {
        initializeDatabase()
    }

    @AfterEach
    fun tearDown() {
        subject.deleteAll().block()
    }

    @Test
    fun whenSaveDriverThenReturnSavedDriver() {
        // given
        val driver = Driver(null, "Jon Snow", LocalDate.ofEpochDay(0))

        // when
        val result = subject.save(driver)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals(driver.name, it.name)
                Assertions.assertEquals(driver.birthdate, it.birthdate)
            }
            .verifyComplete()
    }

    @Test
    fun whenDeleteByIdDriverThenSuccessfullyDeleteDriver() {
        // given
        val driver = Driver(null, "Jon Snow", LocalDate.ofEpochDay(0))
        val driverId = subject.save(driver).blockOptional().get().id ?: throw RuntimeException("DriverId is null")

        // when
        val result = subject.deleteById(driverId)

        // then
        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertFalse(subject.existsById(driverId).blockOptional().get())
                true
            }
            .verifyComplete()
    }

    @Test
    fun whenGetByIdDriverThenReturnDriver() {
        // given
        val driver = Driver(null, "Jon Snow", LocalDate.ofEpochDay(0))
        val driverId = subject.save(driver).blockOptional().get().id ?: throw RuntimeException("DriverId is null")

        // when
        val result = subject.getById(driverId)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(driverId, it.id)
                Assertions.assertEquals("Jon Snow", it.name)
                Assertions.assertEquals(LocalDate.ofEpochDay(0), it.birthdate)
            }
            .verifyComplete()
    }

    @Test
    fun whenFindAllDriversThenReturnAllDrivers() {
        // given
        subject.save(Driver(null, "Jon Snow", LocalDate.ofEpochDay(0)))
        subject.save(Driver(null, "Arya Stark", LocalDate.ofEpochDay(0)))

        // when
        val result = subject.findAll()

        // then
        StepVerifier.create(result)
            .expectNextCount(2)
            .expectComplete()
    }

}