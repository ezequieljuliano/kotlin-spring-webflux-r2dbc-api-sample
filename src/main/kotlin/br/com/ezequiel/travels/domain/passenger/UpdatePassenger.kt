package br.com.ezequiel.travels.domain.passenger

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.model.PassengerToUpdate
import reactor.core.publisher.Mono

interface UpdatePassenger {

    fun execute(passengerToUpdate: PassengerToUpdate): Mono<Passenger>

}