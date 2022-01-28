package br.com.ezequiel.travels.domain.driver.repository

import br.com.ezequiel.travels.domain.driver.model.Driver
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface DriverRepository {

    fun save(driver: Driver): Mono<Driver>

    fun deleteById(driverId: UUID): Mono<Void>

    fun getById(driverId: UUID): Mono<Driver>

    fun findAll(): Flux<Driver>

    fun existsById(driverId: UUID): Mono<Boolean>

    fun deleteAll(): Mono<Void>

}