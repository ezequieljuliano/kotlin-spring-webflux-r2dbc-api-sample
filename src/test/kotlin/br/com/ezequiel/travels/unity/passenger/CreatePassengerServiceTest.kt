package br.com.ezequiel.travels.unity.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.model.PassengerToCreate
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.domain.passenger.service.CreatePassengerService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class CreatePassengerServiceTest {

    private val passengerRepository: PassengerRepository = mockk(relaxed = true)
    private val subject = CreatePassengerService(passengerRepository)
    private val mockedPassenger = Passenger(UUID.randomUUID(), "Jon Snow")

    @Test
    fun whenCreatePassengerThenReturnCreatedPassenger() {
        // given
        val newPassenger = PassengerToCreate(mockedPassenger.name)
        every { passengerRepository.save(any()) } returns Mono.just(mockedPassenger)

        // when
        val result = subject.execute(newPassenger)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(mockedPassenger.id, it.id)
                Assertions.assertEquals(mockedPassenger.name, it.name)
            }
            .verifyComplete()
        verify(exactly = 1) { passengerRepository.save(any()) }
    }

}