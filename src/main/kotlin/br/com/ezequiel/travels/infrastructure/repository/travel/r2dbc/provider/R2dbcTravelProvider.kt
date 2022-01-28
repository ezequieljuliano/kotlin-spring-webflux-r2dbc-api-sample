package br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc.provider

import br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc.entity.TravelEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface R2dbcTravelProvider : R2dbcRepository<TravelEntity, UUID> {

    fun findByStatus(status: String): Flux<TravelEntity>

}