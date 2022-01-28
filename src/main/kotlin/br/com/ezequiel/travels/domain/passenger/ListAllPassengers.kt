package br.com.ezequiel.travels.domain.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import reactor.core.publisher.Flux

interface ListAllPassengers {

    fun execute(): Flux<Passenger>

}