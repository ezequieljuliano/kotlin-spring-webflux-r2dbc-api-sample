package br.com.ezequiel.travels.domain.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.model.DriverToCreate
import reactor.core.publisher.Mono

interface CreateDriver {

    fun execute(driverToCreate: DriverToCreate): Mono<Driver>

}