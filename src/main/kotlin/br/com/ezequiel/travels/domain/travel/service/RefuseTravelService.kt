package br.com.ezequiel.travels.domain.travel.service

import br.com.ezequiel.travels.domain.travel.RefuseTravel
import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class RefuseTravelService(private val travelRepository: TravelRepository) : RefuseTravel {

    override fun execute(travelId: UUID): Mono<Travel> {
        return travelRepository.getById(travelId)
            .map { changeStatusToRefused(it) }
            .flatMap { travelRepository.save(it) }
    }

    private fun changeStatusToRefused(travel: Travel): Travel {
        travel.status = TravelStatus.REFUSED
        return travel
    }

}