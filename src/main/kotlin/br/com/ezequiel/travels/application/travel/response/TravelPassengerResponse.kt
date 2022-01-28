package br.com.ezequiel.travels.application.travel.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class TravelPassengerResponse(

    @field:Schema(description = "Passenger identifier")
    val id: UUID

)
