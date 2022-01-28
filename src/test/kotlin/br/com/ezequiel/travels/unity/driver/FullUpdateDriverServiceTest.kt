package br.com.ezequiel.travels.unity.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.model.DriverToFullUpdate
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.domain.driver.service.FullUpdateDriverService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.util.*

class FullUpdateDriverServiceTest {

    private val driverRepository: DriverRepository = mockk(relaxed = true)
    private val subject = FullUpdateDriverService(driverRepository)
    private val oldMockedDriver = Driver(UUID.randomUUID(), "Jon Snow", LocalDate.MIN)
    private val updatedMockDriver = Driver(oldMockedDriver.id, "Arya Stark", LocalDate.MAX)

    @Test
    fun whenFullUpdateDriverThenReturnUpdatedDriver() {
        // given
        val updatedDriver = DriverToFullUpdate(updatedMockDriver.id!!, updatedMockDriver.name, updatedMockDriver.birthdate)
        every { driverRepository.getById(oldMockedDriver.id!!) } returns Mono.just(oldMockedDriver)
        every { driverRepository.save(any()) } returns Mono.just(updatedMockDriver)

        // when
        val result = subject.execute(updatedDriver)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(updatedMockDriver.id, it.id)
                Assertions.assertEquals(updatedMockDriver.name, it.name)
                Assertions.assertEquals(updatedMockDriver.birthdate, it.birthdate)
            }
            .verifyComplete()
        verify(exactly = 1) { driverRepository.getById(oldMockedDriver.id!!) }
        verify(exactly = 1) { driverRepository.save(any()) }
    }

}