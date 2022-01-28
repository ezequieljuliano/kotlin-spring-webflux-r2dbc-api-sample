package br.com.ezequiel.travels.unity.passenger

import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.domain.passenger.service.DeletePassengerService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class DeletePassengerServiceTest {

    private val passengerRepository: PassengerRepository = mockk(relaxed = true)
    private val subject = DeletePassengerService(passengerRepository)
    private val passengerId = UUID.randomUUID()

    @Test
    fun whenDeletePassengerThenRunSuccessfullyWithoutExceptions() {
        // given
        every { passengerRepository.deleteById(passengerId) } returns Mono.empty()

        // when
        val result = subject.execute(passengerId)

        // then
        StepVerifier.create(result)
            .expectComplete()
            .verify()
        verify(exactly = 1) { passengerRepository.deleteById(passengerId) }
    }

}