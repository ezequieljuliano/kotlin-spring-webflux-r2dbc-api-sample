package br.com.ezequiel.travels.unity.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.model.TravelToCreate
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import br.com.ezequiel.travels.domain.travel.service.CreateTravelService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class CreateTravelServiceTest {

    private val travelRepository: TravelRepository = mockk(relaxed = true)
    private val subject = CreateTravelService(travelRepository)
    private val mockedPassenger = TravelPassenger(UUID.randomUUID())
    private val mockedTravel = Travel(
        UUID.randomUUID(), "Origin", "Destination", mockedPassenger, TravelStatus.CREATED, null
    )

    @Test
    fun whenCreateTravelRequestThenReturnCreatedTravel() {
        // given
        val newTravel = TravelToCreate(
            mockedTravel.origin, mockedTravel.destination, mockedTravel.passenger.id
        )
        every { travelRepository.save(any()) } returns Mono.just(mockedTravel)

        // when
        val result = subject.execute(newTravel)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(mockedTravel.id, it.id)
                Assertions.assertEquals(mockedTravel.origin, it.origin)
                Assertions.assertEquals(mockedTravel.destination, it.destination)
                Assertions.assertEquals(mockedTravel.passenger.id, it.passenger.id)
            }
            .verifyComplete()
        verify(exactly = 1) { travelRepository.save(any()) }
    }

}