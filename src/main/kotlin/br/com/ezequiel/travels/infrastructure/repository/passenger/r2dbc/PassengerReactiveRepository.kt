package br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import br.com.ezequiel.travels.domain.passenger.repository.PassengerRepository
import br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc.entity.toEntity
import br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc.entity.toModel
import br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc.provider.R2dbcPassengerProvider
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class PassengerReactiveRepository(private val r2dbcPassengerProvider: R2dbcPassengerProvider) : PassengerRepository {

    override fun save(passenger: Passenger) = r2dbcPassengerProvider.save(passenger.toEntity()).toModel()

    override fun deleteById(passengerId: UUID): Mono<Void> = r2dbcPassengerProvider.deleteById(passengerId)

    override fun getById(passengerId: UUID) = r2dbcPassengerProvider.findById(passengerId).toModel()

    override fun findAll(): Flux<Passenger> = r2dbcPassengerProvider.findAll().map { it.toModel() }

    override fun existsById(passengerId: UUID) = r2dbcPassengerProvider.existsById(passengerId)

    override fun deleteAll(): Mono<Void> = r2dbcPassengerProvider.deleteAll()

}