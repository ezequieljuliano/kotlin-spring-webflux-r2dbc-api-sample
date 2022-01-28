package br.com.ezequiel.travels.domain.travel.repository

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface TravelRepository {

    fun save(travel: Travel): Mono<Travel>

    fun getById(travelId: UUID): Mono<Travel>

    fun findByStatus(status: TravelStatus): Flux<Travel>

    fun deleteAll(): Mono<Void>

}