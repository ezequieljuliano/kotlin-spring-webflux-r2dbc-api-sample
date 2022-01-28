package br.com.ezequiel.travels.domain.passenger

import reactor.core.publisher.Mono
import java.util.*

interface DeletePassenger {

    fun execute(passengerId: UUID): Mono<Void>

}