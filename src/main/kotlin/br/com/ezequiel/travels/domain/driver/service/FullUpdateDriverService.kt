package br.com.ezequiel.travels.domain.driver.service

import br.com.ezequiel.travels.domain.driver.FullUpdateDriver
import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.model.DriverToFullUpdate
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FullUpdateDriverService(private val driverRepository: DriverRepository) : FullUpdateDriver {

    override fun execute(driverToUpdate: DriverToFullUpdate): Mono<Driver> {
        return driverRepository.getById(driverToUpdate.id)
            .map { copyFullDriver(driverToUpdate, it) }
            .flatMap { driverRepository.save(it) }
    }

    private fun copyFullDriver(source: DriverToFullUpdate, target: Driver) =
        target.copy(name = source.name, birthdate = source.birthdate)

}