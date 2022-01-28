package br.com.ezequiel.travels.domain.travel.model

import java.util.*

data class TravelToCreate(
    val origin: String,
    val destination: String,
    val passengerId: UUID
)

fun TravelToCreate.toTravel() = Travel(
    id = null,
    origin = origin,
    destination = destination,
    passenger = TravelPassenger(passengerId),
    driver = null,
    status = TravelStatus.CREATED
)
