package br.com.ezequiel.travels.unity.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.domain.driver.service.ListAllDriversService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.LocalDate
import java.util.*

class ListAllDriversServiceTest {

    private val driverRepository: DriverRepository = mockk(relaxed = true)
    private val subject = ListAllDriversService(driverRepository)
    private val mockedDrivers = List(2) {
        Driver(UUID.randomUUID(), "Jon Snow", LocalDate.MIN)
        Driver(UUID.randomUUID(), "Arya Stark", LocalDate.MAX)
    }

    @Test
    fun whenListDriversThenReturnDriversSuccessfully() {
        // given
        every { driverRepository.findAll() } returns Flux.just(mockedDrivers[0], mockedDrivers[1])

        // when
        val result = subject.execute()

        // then
        StepVerifier.create(result)
            .thenConsumeWhile {
                Assertions.assertTrue(mockedDrivers.stream().anyMatch { p -> p.id == it.id })
                Assertions.assertTrue(mockedDrivers.stream().anyMatch { p -> p.name == it.name })
                Assertions.assertTrue(mockedDrivers.stream().anyMatch { p -> p.birthdate == it.birthdate })
                true
            }
            .verifyComplete()
        verify(exactly = 1) { driverRepository.findAll() }
    }

}