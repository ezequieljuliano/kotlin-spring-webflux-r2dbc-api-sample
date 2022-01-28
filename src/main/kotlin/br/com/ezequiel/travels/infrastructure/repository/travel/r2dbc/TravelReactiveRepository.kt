package br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import br.com.ezequiel.travels.domain.travel.repository.TravelRepository
import br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc.entity.toEntity
import br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc.entity.toModel
import br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc.provider.R2dbcTravelProvider
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class TravelReactiveRepository(private val r2dbcTravelProvider: R2dbcTravelProvider) : TravelRepository {

    override fun save(travel: Travel) = r2dbcTravelProvider.save(travel.toEntity()).toModel()

    override fun getById(travelId: UUID) = r2dbcTravelProvider.findById(travelId).toModel()

    override fun findByStatus(status: TravelStatus): Flux<Travel> =
        r2dbcTravelProvider.findByStatus(status.name).map { it.toModel() }

    override fun deleteAll(): Mono<Void> = r2dbcTravelProvider.deleteAll()

}