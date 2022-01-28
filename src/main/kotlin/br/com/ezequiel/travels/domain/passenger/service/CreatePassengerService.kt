package br.com.ezequiel.travels.domain.passenger.service

import br.com.ezequiel.travels.domain.passenger.CreatePassenger
import br.com.ezequiel.travels.domain.passenger.model.PassengerToCreate
import br.com.ezequiel.travels.domain.passenger.model.toPassenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import org.springframework.stereotype.Service

@Service
class CreatePassengerService(private val passengerRepository: PassengerRepository) : CreatePassenger {

    override fun execute(passengerToCreate: PassengerToCreate) =
        passengerRepository.save(passengerToCreate.toPassenger())

}