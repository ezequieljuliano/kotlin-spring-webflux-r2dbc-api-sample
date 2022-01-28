package br.com.ezequiel.travels.domain.passenger.model

data class PassengerToCreate(

    val name: String

)

fun PassengerToCreate.toPassenger() = Passenger(
    id = null,
    name = name
)
