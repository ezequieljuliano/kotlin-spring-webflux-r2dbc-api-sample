package br.com.ezequiel.travels.integration

import br.com.ezequiel.travels.ApplicationContextTest
import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
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
class TravelRepositoryTest : ApplicationContextTest() {

    @Autowired
    private lateinit var passengerRepository: PassengerRepository

    @Autowired
    private lateinit var subject: TravelRepository

    @BeforeAll
    fun setup() {
        initializeDatabase()
    }

    @AfterEach
    fun tearDown() {
        subject.deleteAll().block()
    }

    @Test
    fun whenSaveTravelThenReturnSavedTravel() {
        // given
        val passengerId = passengerRepository.save(Passenger(null, "Jon Snow")).blockOptional().get().id
            ?: throw RuntimeException("PassengerId is null")
        val travelPassenger = TravelPassenger(passengerId)
        val travel = Travel(null, "Origin", "Destination", travelPassenger, TravelStatus.CREATED, null)

        // when
        val result = subject.save(travel)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals(travel.origin, it.origin)
                Assertions.assertEquals(travel.destination, it.destination)
                Assertions.assertEquals(travel.passenger.id, it.passenger.id)
                Assertions.assertNull(it.driver)
                Assertions.assertEquals(TravelStatus.CREATED, it.status)
            }
            .verifyComplete()
    }

    @Test
    fun whenGetByIdTravelThenReturnTravel() {
        // given
        val passengerId = passengerRepository.save(Passenger(null, "Jon Snow")).blockOptional().get().id
            ?: throw RuntimeException("PassengerId is null")
        val travelPassenger = TravelPassenger(passengerId)
        val travel = Travel(null, "Origin", "Destination", travelPassenger, TravelStatus.CREATED, null)
        val id = subject.save(travel).blockOptional().get().id ?: throw RuntimeException("TravelId is null")

        // when
        val result = subject.getById(id)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals(travel.origin, it.origin)
                Assertions.assertEquals(travel.destination, it.destination)
                Assertions.assertEquals(travel.passenger.id, it.passenger.id)
                Assertions.assertNull(it.driver)
                Assertions.assertEquals(TravelStatus.CREATED, it.status)
            }
            .verifyComplete()
    }

    @Test
    fun whenFindTravelsByStatusThenReturnTravelsList() {
        // given
        val passengerId = passengerRepository.save(Passenger(null, "Jon Snow")).blockOptional().get().id
            ?: throw RuntimeException("PassengerId is null")
        val travelPassenger = TravelPassenger(passengerId)
        subject.save(Travel(null, "Origin1", "Destination1", travelPassenger, TravelStatus.CREATED, null))
        subject.save(Travel(null, "Origin2", "Destination2", travelPassenger, TravelStatus.CREATED, null))

        // when
        val result = subject.findByStatus(TravelStatus.CREATED)

        // then
        StepVerifier.create(result)
            .expectNextCount(2)
            .expectComplete()
    }

}