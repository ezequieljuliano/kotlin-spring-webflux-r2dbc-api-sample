package br.com.ezequiel.travels.domain.passenger.service

import br.com.ezequiel.travels.domain.passenger.UpdatePassenger
import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.model.PassengerToUpdate
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UpdatePassengerService(private val passengerRepository: PassengerRepository) : UpdatePassenger {

    override fun execute(passengerToUpdate: PassengerToUpdate): Mono<Passenger> {
        return passengerRepository.getById(passengerToUpdate.id)
            .map { copyFullPassenger(passengerToUpdate, it) }
            .flatMap { passengerRepository.save(it) }
    }

    private fun copyFullPassenger(source: PassengerToUpdate, target: Passenger) = target.copy(name = source.name)

}