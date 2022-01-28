package br.com.ezequiel.travels.domain.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.model.DriverToFullUpdate
import reactor.core.publisher.Mono

interface FullUpdateDriver {

    fun execute(driverToUpdate: DriverToFullUpdate): Mono<Driver>

}