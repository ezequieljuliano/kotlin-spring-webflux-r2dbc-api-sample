package br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc.entity

import br.com.ezequiel.travels.domain.driver.model.Driver
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Size

@Table("driver")
data class DriverEntity(

    @field:Id
    @field:Column("drv_id")
    val id: UUID?,

    @field:NotEmpty
    @field:Size(min = 5, max = 255)
    @field:Column("drv_name")
    val name: String,

    @field:NotNull
    @field:Past
    @field:Column("drv_birthdate")
    val birthdate: LocalDate

)

fun Driver.toEntity() = DriverEntity(
    id = id,
    name = name,
    birthdate = birthdate
)

fun DriverEntity.toModel() = Driver(
    id = id,
    name = name,
    birthdate = birthdate
)

fun Mono<DriverEntity>.toModel() = map { Driver(it.id, it.name, it.birthdate) }
