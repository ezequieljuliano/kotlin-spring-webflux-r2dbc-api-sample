package br.com.ezequiel.travels.domain.travel

import br.com.ezequiel.travels.domain.travel.model.Travel
import reactor.core.publisher.Flux

interface ListAvailableTravels {

    fun execute(): Flux<Travel>

}