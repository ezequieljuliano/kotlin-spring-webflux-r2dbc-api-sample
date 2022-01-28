package br.com.ezequiel.travels.infrastructure.repository.travel.r2dbc.entity

import br.com.ezequiel.travels.domain.travel.model.Travel
import br.com.ezequiel.travels.domain.travel.model.TravelDriver
import br.com.ezequiel.travels.domain.travel.model.TravelPassenger
import br.com.ezequiel.travels.domain.travel.model.TravelStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Table("travel")
data class TravelEntity(

    @field:Id
    @field:Column("trv_id")
    val id: UUID?,

    @field:NotEmpty
    @field:Size(min = 5, max = 255)
    @field:Column("trv_origin")
    val origin: String,

    @field:NotEmpty
    @field:Size(min = 5, max = 255)
    @field:Column("trv_destination")
    val destination: String,

    @field:NotNull
    @field:Column("trv_psg_id")
    val passengerId: UUID,

    @field:Column("trv_drv_id")
    var driverId: UUID? = null,

    @field:NotNull
    @field:Column("trv_status")
    var status: String = TravelStatus.CREATED.name,

    @field:NotNull
    @field:Column("trv_created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

)

fun Travel.toEntity() = TravelEntity(
    id = id,
    origin = origin,
    destination = destination,
    passengerId = passenger.id,
    driverId = driver?.id
)

fun TravelEntity.toModel() = Travel(
    id = id,
    origin = origin,
    destination = destination,
    passenger = TravelPassenger(passengerId),
    driver = driverId?.let { TravelDriver(driverId!!) },
    status = TravelStatus.valueOf(status)
)

fun Mono<TravelEntity>.toModel() = map { travel ->
    Travel(
        id = travel.id,
        origin = travel.origin,
        destination = travel.destination,
        passenger = TravelPassenger(travel.passengerId),
        driver = travel.driverId?.let { TravelDriver(it) },
        status = TravelStatus.valueOf(travel.status)
    )
}