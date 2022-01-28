package br.com.ezequiel.travels.component

import br.com.ezequiel.travels.ApplicationContextTest
import br.com.ezequiel.travels.application.travel.request.TravelDriverRequest
import br.com.ezequiel.travels.application.travel.request.TravelToCreateRequest
import br.com.ezequiel.travels.application.travel.response.TravelResponse
import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelDriver
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.test.StepVerifier
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TravelRequestControllerTest : ApplicationContextTest() {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var travelRepository: TravelRepository

    @Autowired
    private lateinit var passengerRepository: PassengerRepository

    @Autowired
    private lateinit var driverRepository: DriverRepository

    private lateinit var client: WebTestClient

    @BeforeAll
    fun setup() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:$port/travels-api").build()
        initializeDatabase()
    }

    @AfterEach
    fun tearDown() {
        travelRepository.deleteAll().block()
    }

    @Test
    fun whenCreateTravelRequestThenReturnStatusCreatedAndResponseBody() {
        val passengerId = passengerRepository.save(Passenger(null, "Ezequiel")).blockOptional().get().id ?: throw RuntimeException("PassengerId is null")
        client.post().uri("/travel-requests")
            .bodyValue(TravelToCreateRequest("Maravilha", "Campinas", passengerId))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.origin").isEqualTo("Maravilha")
            .jsonPath("$.destination").isEqualTo("Campinas")
            .jsonPath("$.passenger.id").isEqualTo(passengerId.toString())
    }

    @Test
    fun whenAcceptTravelRequestThenReturnStatusNoContent() {
        val passengerId = passengerRepository.save(Passenger(null, "Jon Snow")).blockOptional().get().id ?: throw RuntimeException("PassengerId is null")
        val travelPassenger = TravelPassenger(passengerId)
        val travel = Travel(null, "Origin", "Destination", travelPassenger, TravelStatus.CREATED, null)
        val travelId = travelRepository.save(travel).blockOptional().get().id
        val driverId = driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).blockOptional().get().id ?: throw RuntimeException("DriverId is null")
        client.put().uri("/travel-requests/$travelId/accept")
            .bodyValue(TravelDriverRequest(driverId))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenRefuseTravelRequestThenReturnStatusNoContent() {
        val passengerId = passengerRepository.save(Passenger(null, "Jon Snow")).blockOptional().get().id ?: throw RuntimeException("PassengerId is null")
        val travelPassenger = TravelPassenger(passengerId)
        val driverId = driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).blockOptional().get().id ?: throw RuntimeException("DriverId is null")
        val travelDriver = TravelDriver(driverId)
        val travel = Travel(null, "Origin", "Destination", travelPassenger, TravelStatus.ACCEPTED, travelDriver)
        val travelId = travelRepository.save(travel).blockOptional().get().id
        client.put().uri("/travel-requests/$travelId/refuse")
            .bodyValue(TravelDriverRequest(driverId))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenListTravelRequestsThenReturnStatusOkAndResponseBody() {
        val passengerId = passengerRepository.save(Passenger(null, "Jon Snow")).blockOptional().get().id ?: throw RuntimeException("PassengerId is null")
        val travelPassenger = TravelPassenger(passengerId)
        val travel = Travel(null, "Maravilha", "Campinas", travelPassenger, TravelStatus.CREATED, null)
        travelRepository.save(travel).block()

        val result = client.get().uri("/travel-requests/available")
            .exchange()
            .expectStatus().isOk
            .returnResult<TravelResponse>()
            .responseBody

        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals("Maravilha", it.origin)
                Assertions.assertEquals("Campinas", it.destination)
                Assertions.assertEquals(passengerId, it.passenger.id)
                true
            }
            .verifyComplete()
    }

}