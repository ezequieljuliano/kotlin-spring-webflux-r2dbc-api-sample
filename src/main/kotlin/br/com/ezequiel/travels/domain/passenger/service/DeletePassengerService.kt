package br.com.ezequiel.travels.domain.passenger.service

import br.com.ezequiel.travels.domain.passenger.DeletePassenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class DeletePassengerService(private val passengerRepository: PassengerRepository) : DeletePassenger {

    override fun execute(passengerId: UUID): Mono<Void> = passengerRepository.deleteById(passengerId)

}