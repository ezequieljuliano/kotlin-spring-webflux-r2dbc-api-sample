package br.com.ezequiel.travels.domain.driver

import br.com.ezequiel.travels.domain.driver.model.Driver
import reactor.core.publisher.Flux

interface ListAllDrivers {

    fun execute(): Flux<Driver>

}