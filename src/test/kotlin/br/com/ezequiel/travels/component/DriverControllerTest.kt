package br.com.ezequiel.travels.component

import br.com.ezequiel.travels.ApplicationContextTest
import br.com.ezequiel.travels.application.driver.request.DriverToCreateRequest
import br.com.ezequiel.travels.application.driver.request.DriverToFullUpdateRequest
import br.com.ezequiel.travels.application.driver.request.DriverToPartialUpdateRequest
import br.com.ezequiel.travels.application.driver.response.DriverResponse
import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
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
class DriverControllerTest : ApplicationContextTest() {

    @LocalServerPort
    private var port: Int = 0

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
        driverRepository.deleteAll().block()
    }

    @Test
    fun whenCreateDriverThenReturnStatusCreatedAndResponseBody() {
        client.post().uri("/drivers")
            .bodyValue(DriverToCreateRequest("Ezequiel", LocalDate.ofEpochDay(0)))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.name").isEqualTo("Ezequiel")
            .jsonPath("$.birthdate").isEqualTo("1970-01-01")
    }

    @Test
    fun whenFullUpdateDriverThenReturnStatusNoContent() {
        val driverId = driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).blockOptional().get().id
        client.put().uri("/drivers/$driverId")
            .bodyValue(DriverToFullUpdateRequest("Juliano", LocalDate.ofEpochDay(0)))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenPartialUpdateDriverThenReturnStatusNoContent() {
        val driverId = driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).blockOptional().get().id
        client.patch().uri("/drivers/$driverId")
            .bodyValue(DriverToPartialUpdateRequest("Juliano", null))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenDeleteDriverThenReturnStatusNoContent() {
        val driverId = driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).blockOptional().get().id
        client.delete().uri("/drivers/$driverId")
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun whenGetDriverThenReturnStatusOkAndResponseBody() {
        val driverId = driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).blockOptional().get().id
        client.get().uri("/drivers/$driverId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.name").isEqualTo("Ezequiel")
            .jsonPath("$.birthdate").isEqualTo("1970-01-01")
    }

    @Test
    fun whenListDriversThenReturnStatusOkAndResponseBody() {
        driverRepository.save(Driver(null, "Ezequiel", LocalDate.ofEpochDay(0))).block()

        val result = client.get().uri("/drivers")
            .exchange()
            .expectStatus().isOk
            .returnResult<DriverResponse>()
            .responseBody

        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertNotNull(it.id)
                Assertions.assertEquals("Ezequiel", it.name)
                Assertions.assertEquals(LocalDate.ofEpochDay(0), it.birthdate)
                true
            }
            .verifyComplete()
    }

}