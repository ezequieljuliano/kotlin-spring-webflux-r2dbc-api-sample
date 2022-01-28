package br.com.ezequiel.travels.domain.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.model.PassengerToCreate
import reactor.core.publisher.Mono

interface CreatePassenger {

    fun execute(passengerToCreate: PassengerToCreate): Mono<Passenger>

}