package br.com.ezequiel.travels.unity.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import br.com.ezequiel.travels.domain.travel.service.ListAvailableTravelsService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

class ListAvailableTravelsServiceTest {

    private val travelRepository: TravelRepository = mockk(relaxed = true)
    private val subject = ListAvailableTravelsService(travelRepository)
    private val mockedPassenger = TravelPassenger(UUID.randomUUID())
    private val mockedTravels = List(2) {
        Travel(UUID.randomUUID(), "Origin1", "Destination1", mockedPassenger, TravelStatus.CREATED, null)
        Travel(UUID.randomUUID(), "Origin2", "Destination2", mockedPassenger, TravelStatus.CREATED, null)
    }

    @Test
    fun whenListAvailableTravelsThenReturnListSuccessfully() {
        // given
        every { travelRepository.findByStatus(TravelStatus.CREATED) } returns Flux.just(
            mockedTravels[0],
            mockedTravels[1]
        )

        // when
        val result = subject.execute()

        // then
        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertTrue(mockedTravels.stream().anyMatch { p -> p.id == it.id })
                Assertions.assertTrue(mockedTravels.stream().anyMatch { p -> p.origin == it.origin })
                Assertions.assertTrue(mockedTravels.stream().anyMatch { p -> p.destination == it.destination })
                Assertions.assertTrue(mockedTravels.stream().anyMatch { p -> p.passenger.id == it.passenger.id })
                Assertions.assertTrue(mockedTravels.stream().anyMatch { p -> p.driver?.id == it.driver?.id })
                Assertions.assertTrue(mockedTravels.stream().anyMatch { p -> p.status == TravelStatus.CREATED })
                true
            }
            .verifyComplete()
        verify(exactly = 1) { travelRepository.findByStatus(TravelStatus.CREATED) }
    }

}