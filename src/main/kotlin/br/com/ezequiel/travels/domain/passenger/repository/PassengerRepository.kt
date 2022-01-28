package br.com.ezequiel.travels.domain.passenger.repository

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PassengerRepository {

    fun save(passenger: Passenger): Mono<Passenger>

    fun deleteById(passengerId: UUID): Mono<Void>

    fun getById(passengerId: UUID): Mono<Passenger>

    fun findAll(): Flux<Passenger>

    fun existsById(passengerId: UUID): Mono<Boolean>

    fun deleteAll(): Mono<Void>

}