package br.com.ezequiel.travels.domain.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelToCreate
import reactor.core.publisher.Mono

interface CreateTravel {

    fun execute(travelToCreate: TravelToCreate): Mono<Travel>

}