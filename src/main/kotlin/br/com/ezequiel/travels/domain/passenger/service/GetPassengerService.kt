package br.com.ezequiel.travels.domain.passenger.service

import br.com.ezequiel.travels.domain.passenger.GetPassenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetPassengerService(private val passengerRepository: PassengerRepository) : GetPassenger {

    override fun execute(passengerId: UUID) = passengerRepository.getById(passengerId)

}