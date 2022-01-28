package br.com.ezequiel.travels.domain.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import reactor.core.publisher.Mono
import java.util.*

interface RefuseTravel {

    fun execute(travelId: UUID): Mono<Travel>

}