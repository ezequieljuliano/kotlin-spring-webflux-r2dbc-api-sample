package br.com.ezequiel.travels.unity.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelDriver
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import br.com.ezequiel.travels.domain.travel.service.RefuseTravelService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class RefuseTravelServiceTest {

    private val travelRepository: TravelRepository = mockk(relaxed = true)
    private val subject = RefuseTravelService(travelRepository)
    private val mockedPassenger = TravelPassenger(UUID.randomUUID())
    private val mockedDriver = TravelDriver(UUID.randomUUID())
    private val mockedTravel = Travel(
        UUID.randomUUID(), "Origin", "Destination", mockedPassenger, TravelStatus.ACCEPTED, mockedDriver
    )

    @Test
    fun whenRefuseTravelRequestThenRunSuccessfullyWithoutExceptions() {
        // given
        every { travelRepository.getById(any()) } returns Mono.just(mockedTravel)
        every { travelRepository.save(any()) } returns Mono.just(mockedTravel)

        // when
        val result = subject.execute(UUID.randomUUID())

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(mockedTravel.id, it.id)
                Assertions.assertEquals(mockedTravel.origin, it.origin)
                Assertions.assertEquals(mockedTravel.destination, it.destination)
                Assertions.assertEquals(mockedTravel.passenger.id, it.passenger.id)
                Assertions.assertEquals(TravelStatus.REFUSED, it.status)
            }
            .verifyComplete()
        verify(exactly = 1) { travelRepository.save(any()) }
        verify(exactly = 1) { travelRepository.getById(any()) }
    }

}