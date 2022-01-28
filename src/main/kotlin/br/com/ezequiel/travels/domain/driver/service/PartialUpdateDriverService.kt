package br.com.ezequiel.travels.domain.driver.service

import br.com.ezequiel.travels.domain.driver.PartialUpdateDriver
import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.model.DriverToPartialUpdate
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PartialUpdateDriverService(private val driverRepository: DriverRepository) : PartialUpdateDriver {

    override fun execute(driverToUpdate: DriverToPartialUpdate): Mono<Driver> {
        return driverRepository.getById(driverToUpdate.id)
            .map { copyPartialDriver(driverToUpdate, it) }
            .flatMap { driverRepository.save(it) }
    }

    private fun copyPartialDriver(source: DriverToPartialUpdate, target: Driver) =
        target.copy(
            name = source.name ?: target.name,
            birthdate = source.birthdate ?: target.birthdate
        )

}