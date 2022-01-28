package br.com.ezequiel.travels.unity.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import br.com.ezequiel.travels.domain.travel.service.AcceptTravelService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class AcceptTravelServiceTest {

    private val travelRepository: TravelRepository = mockk(relaxed = true)
    private val subject = AcceptTravelService(travelRepository)
    private val mockedPassenger = TravelPassenger(UUID.randomUUID())
    private val mockedTravel = Travel(
        UUID.randomUUID(), "Origin", "Destination", mockedPassenger, TravelStatus.CREATED, null
    )

    @Test
    fun whenAcceptTravelRequestThenRunSuccessfullyWithoutExceptions() {
        // given
        every { travelRepository.getById(any()) } returns Mono.just(mockedTravel)
        every { travelRepository.save(any()) } returns Mono.just(mockedTravel)

        // when
        val result = subject.execute(UUID.randomUUID(), UUID.randomUUID())

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(mockedTravel.id, it.id)
                Assertions.assertEquals(mockedTravel.origin, it.origin)
                Assertions.assertEquals(mockedTravel.destination, it.destination)
                Assertions.assertEquals(mockedTravel.passenger.id, it.passenger.id)
                Assertions.assertEquals(mockedTravel.driver?.id, it.driver?.id)
                Assertions.assertEquals(TravelStatus.ACCEPTED, it.status)
            }
            .verifyComplete()
        verify(exactly = 1) { travelRepository.save(any()) }
        verify(exactly = 1) { travelRepository.getById(any()) }
    }

}