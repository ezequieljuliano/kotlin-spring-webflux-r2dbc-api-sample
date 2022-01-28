package br.com.ezequiel.travels.unity.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.model.DriverToCreate
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.domain.driver.service.CreateDriverService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.util.*

class CreateDriverServiceTest {

    private val driverRepository: DriverRepository = mockk(relaxed = true)
    private val subject = CreateDriverService(driverRepository)
    private val mockedDriver = Driver(UUID.randomUUID(), "Jon Snow", LocalDate.MIN)

    @Test
    fun whenCreateDriverThenReturnCreatedDriver() {
        // given
        val newDriver = DriverToCreate(mockedDriver.name, mockedDriver.birthdate)
        every { driverRepository.save(any()) } returns Mono.just(mockedDriver)

        // when
        val result = subject.execute(newDriver)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(mockedDriver.id, it.id)
                Assertions.assertEquals(mockedDriver.name, it.name)
                Assertions.assertEquals(mockedDriver.birthdate, it.birthdate)
            }
            .verifyComplete()
        verify(exactly = 1) { driverRepository.save(any()) }
    }

}