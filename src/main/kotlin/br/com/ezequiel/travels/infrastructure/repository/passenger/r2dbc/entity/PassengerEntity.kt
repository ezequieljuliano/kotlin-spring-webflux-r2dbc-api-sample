package br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc.entity

import br.com.ezequiel.travels.domain.passenger.model.Passenger
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import reactor.core.publisher.Mono
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Table("passenger")
data class PassengerEntity(

    @field:Id
    @field:Column("psg_id")
    val id: UUID?,

    @field:NotEmpty
    @field:Size(min = 5, max = 255)
    @field:Column("psg_name")
    val name: String

)

fun Passenger.toEntity() = PassengerEntity(
    id = id,
    name = name
)

fun PassengerEntity.toModel() = Passenger(
    id = id,
    name = name
)

fun Mono<PassengerEntity>.toModel() = map { Passenger(it.id, it.name) }