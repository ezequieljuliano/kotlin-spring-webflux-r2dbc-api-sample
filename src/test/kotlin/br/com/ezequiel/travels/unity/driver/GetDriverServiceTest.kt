package br.com.ezequiel.travels.unity.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.domain.driver.service.GetDriverService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.util.*

class GetDriverServiceTest {

    private val driverRepository: DriverRepository = mockk(relaxed = true)
    private val subject = GetDriverService(driverRepository)
    private val mockedDriver = Driver(UUID.randomUUID(), "Jon Snow", LocalDate.MIN)
    private val driverId = mockedDriver.id

    @Test
    fun whenGetDriverThenReturnDriverSuccessfully() {
        // given
        every { driverRepository.getById(driverId!!) } returns Mono.just(mockedDriver)

        // when
        val result = subject.execute(driverId!!)

        // then
        StepVerifier.create(result)
            .assertNext {
                Assertions.assertEquals(mockedDriver.id, it.id)
                Assertions.assertEquals(mockedDriver.name, it.name)
                Assertions.assertEquals(mockedDriver.birthdate, it.birthdate)
            }
            .verifyComplete()
        verify(exactly = 1) { driverRepository.getById(driverId) }
    }

}