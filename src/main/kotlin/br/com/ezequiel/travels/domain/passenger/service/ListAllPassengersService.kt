package br.com.ezequiel.travels.domain.passenger.service

import br.com.ezequiel.travels.domain.passenger.ListAllPassengers
import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ListAllPassengersService(private val passengerRepository: PassengerRepository) : ListAllPassengers {

    override fun execute(): Flux<Passenger> = passengerRepository.findAll()

}