package br.com.ezequiel.travels.unity.driver

import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.domain.driver.service.DeleteDriverService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

class DeleteDriverServiceTest {

    private val driverRepository: DriverRepository = mockk(relaxed = true)
    private val subject = DeleteDriverService(driverRepository)
    private val driverId = UUID.randomUUID()

    @Test
    fun whenDeleteDriverThenRunSuccessfullyWithoutExceptions() {
        // given
        every { driverRepository.deleteById(driverId) } returns Mono.empty()

        // when
        val result = subject.execute(driverId)

        // then
        StepVerifier.create(result)
            .expectComplete()
            .verify()
        verify(exactly = 1) { driverRepository.deleteById(driverId) }
    }

}