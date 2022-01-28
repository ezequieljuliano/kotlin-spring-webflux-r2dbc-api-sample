package br.com.ezequiel.travels.domain.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import reactor.core.publisher.Mono
import java.util.*

interface GetPassenger {

    fun execute(passengerId: UUID): Mono<Passenger>

}