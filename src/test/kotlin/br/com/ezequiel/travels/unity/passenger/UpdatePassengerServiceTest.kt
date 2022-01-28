package br.com.ezequiel.travels.unity.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.model.PassengerToUpdate
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.domain.passenger.service.UpdatePassengerService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class UpdatePassengerServiceTest {

    private val passengerRepository: PassengerRepository = mockk(relaxed = true)
    private val subject = UpdatePassengerService(passengerRepository)
    private val oldMockedPassenger = Passenger(UUID.randomUUID(), "Jon Snow")
    private val updatedMockPassenger = Passenger(oldMockedPassenger.id, "Arya Stark")

    @Test
    fun whenFullUpdatePassengerThenReturnUpdatedPassenger() {
        // given
        val updatedPassenger = PassengerToUpdate(updatedMockPassenger.id!!, updatedMockPassenger.name)
        every { passengerRepository.getById(oldMockedPassenger.id!!) } returns Mono.just(oldMockedPassenger)
        every { passengerRepository.save(any()) } returns Mono.just(updatedMockPassenger)

        // when
        val result = subject.execute(updatedPassenger)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(updatedMockPassenger.id, it.id)
                Assertions.assertEquals(updatedMockPassenger.name, it.name)
            }
            .verifyComplete()
        verify(exactly = 1) { passengerRepository.getById(oldMockedPassenger.id!!) }
        verify(exactly = 1) { passengerRepository.save(any()) }
    }

}