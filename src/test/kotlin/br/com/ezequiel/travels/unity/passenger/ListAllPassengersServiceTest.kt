package br.com.ezequiel.travels.unity.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.domain.passenger.service.ListAllPassengersService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

class ListAllPassengersServiceTest {

    private val passengerRepository: PassengerRepository = mockk(relaxed = true)
    private val subject = ListAllPassengersService(passengerRepository)
    private val mockedPassengers = List(2) {
        Passenger(UUID.randomUUID(), "Jon Snow")
        Passenger(UUID.randomUUID(), "Arya Stark")
    }

    @Test
    fun whenListPassengersThenReturnPassengersSuccessfully() {
        // given
        every { passengerRepository.findAll() } returns Flux.just(mockedPassengers[0], mockedPassengers[1])

        // when
        val result = subject.execute()

        // then
        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertTrue(mockedPassengers.stream().anyMatch { p -> p.id == it.id })
                Assertions.assertTrue(mockedPassengers.stream().anyMatch { p -> p.name == it.name })
                true
            }
            .verifyComplete()
        verify(exactly = 1) { passengerRepository.findAll() }
    }

}