package br.com.ezequiel.travels.component

import br.com.ezequiel.travels.ApplicationContextTest
import br.com.ezequiel.travels.application.passenger.request.PassengerToCreateRequest
import br.com.ezequiel.travels.application.passenger.request.PassengerToUpdateRequest
import br.com.ezequiel.travels.application.passenger.response.PassengerResponse
import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PassengerControllerTest : ApplicationContextTest() {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var passengerRepository: PassengerRepository

    private lateinit var client: WebTestClient

    @BeforeAll
    fun setup() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:$port/travels-api").build()
        initializeDatabase()
    }

    @AfterEach
    fun tearDown() {
        passengerRepository.deleteAll().block()
    }

    @Test
    fun whenCreatePassengerThenReturnStatusCreatedAndResponseBody() {
        client.post().uri("/passengers")
            .bodyValue(PassengerToCreateRequest("Ezequiel"))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.name").isEqualTo("Ezequiel")
    }

    @Test
    fun whenUpdatePassengerThenReturnStatusNoContent() {
        val passengerId = passengerRepository.save(Passenger(null, "Ezequiel")).blockOptional().get().id
        client.put().uri("/passengers/$passengerId")
            .bodyValue(PassengerToUpdateRequest("Juliano"))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenDeletePassengerThenReturnStatusNoContent() {
        val passengerId = passengerRepository.save(Passenger(null, "Ezequiel")).blockOptional().get().id
        client.delete().uri("/passengers/$passengerId")
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenGetPassengerThenReturnStatusOkAndResponseBody() {
        val passengerId = passengerRepository.save(Passenger(null, "Ezequiel")).blockOptional().get().id
        client.get().uri("/passengers/$passengerId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.name").isEqualTo("Ezequiel")
    }

    @Test
    fun whenListPassengersThenReturnStatusOkAndResponseBody() {
        passengerRepository.save(Passenger(null, "Ezequiel")).block()

        val result = client.get().uri("/passengers")
            .exchange()
            .expectStatus().isOk
            .returnResult<PassengerResponse>()
            .responseBody

        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals("Ezequiel", it.name)
                true
            }
            .verifyComplete()
    }

}