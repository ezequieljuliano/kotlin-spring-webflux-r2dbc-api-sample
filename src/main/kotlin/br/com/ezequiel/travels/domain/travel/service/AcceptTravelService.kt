package br.com.ezequiel.travels.domain.travel.service

import br.com.ezequiel.travels.domain.travel.AcceptTravel
import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelDriver
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class AcceptTravelService(private val travelRepository: TravelRepository) : AcceptTravel {

    override fun execute(travelId: UUID, driverId: UUID): Mono<Travel> {
        return travelRepository.getById(travelId)
            .map { changeStatusToAcceptedAndSetDriver(it, driverId) }
            .flatMap { travelRepository.save(it) }
    }

    private fun changeStatusToAcceptedAndSetDriver(travel: Travel, driverId: UUID): Travel {
        travel.status = TravelStatus.ACCEPTED
        travel.driver = TravelDriver(driverId)
        return travel
    }

}