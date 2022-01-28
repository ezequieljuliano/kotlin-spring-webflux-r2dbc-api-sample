package br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc

import br.com.ezequiel.travels.domain.driver.model.Driver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc.entity.toEntity
import br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc.entity.toModel
import br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc.provider.R2dbcDriverProvider
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class DriverReactiveRepository(private val r2dbcDriverProvider: R2dbcDriverProvider) : DriverRepository {

    override fun save(driver: Driver) = r2dbcDriverProvider.save(driver.toEntity()).toModel()

    override fun deleteById(driverId: UUID): Mono<Void> = r2dbcDriverProvider.deleteById(driverId)

    override fun getById(driverId: UUID) = r2dbcDriverProvider.findById(driverId).toModel()

    override fun findAll(): Flux<Driver> = r2dbcDriverProvider.findAll().map { it.toModel() }

    override fun existsById(driverId: UUID) = r2dbcDriverProvider.existsById(driverId)

    override fun deleteAll(): Mono<Void> = r2dbcDriverProvider.deleteAll()

}