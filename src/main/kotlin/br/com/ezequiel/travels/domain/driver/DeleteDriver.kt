package br.com.ezequiel.travels.domain.driver

import reactor.core.publisher.Mono
import java.util.*

interface DeleteDriver {

    fun execute(driverId: UUID): Mono<Void>

}