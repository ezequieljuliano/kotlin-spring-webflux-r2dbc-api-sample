package br.com.ezequiel.travels.integration

import br.com.ezequiel.travels.ApplicationContextTest
import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PassengerRepositoryTest : ApplicationContextTest() {

    @Autowired
    private lateinit var subject: PassengerRepository

    @BeforeAll
    fun setup() {
        initializeDatabase()
    }

    @AfterEach
    fun tearDown() {
        subject.deleteAll().block()
    }

    @Test
    fun whenSavePassengerThenReturnSavedPassenger() {
        // given
        val passenger = Passenger(null, "Jon Snow")

        // when
        val result = subject.save(passenger)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals(passenger.name, it.name)
            }
            .verifyComplete()
    }

    @Test
    fun whenDeleteByIdPassengerThenSuccessfullyDeletePassenger() {
        // given
        val passenger = Passenger(null, "Jon Snow")
        val passengerId =
            subject.save(passenger).blockOptional().get().id ?: throw RuntimeException("PassengerId is null")

        // when
        val result = subject.deleteById(passengerId)

        // then
        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertFalse(subject.existsById(passengerId).blockOptional().get())
                true
            }
            .verifyComplete()
    }

    @Test
    fun whenGetByIdPassengerThenReturnPassenger() {
        // given
        val passenger = Passenger(null, "Jon Snow")
        val passengerId =
            subject.save(passenger).blockOptional().get().id ?: throw RuntimeException("PassengerId is null")

        // when
        val result = subject.getById(passengerId)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals(passenger.name, it.name)
            }
            .verifyComplete()
    }

    @Test
    fun whenFindAllPassengersThenReturnAllPassengers() {
        // given
        subject.save(Passenger(null, "Jon Snow"))
        subject.save(Passenger(null, "Arya Stark"))

        // when
        val result = subject.findAll()

        // then
        StepVerifier.create(result)
            .expectNextCount(2)
            .expectComplete()
    }

}