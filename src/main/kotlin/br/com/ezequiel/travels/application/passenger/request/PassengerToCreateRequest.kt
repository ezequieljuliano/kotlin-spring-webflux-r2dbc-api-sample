package br.com.ezequiel.travels.application.passenger.request

import br.com.ezequiel.travels.domain.passenger.model.PassengerToCreate
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class PassengerToCreateRequest(

    @field:NotEmpty
    @field:Size(min = 5, max = 255)
    @field:Schema(description = "Passenger name")
    val name: String

)

fun PassengerToCreateRequest.toModel() = PassengerToCreate(
    name = name
)
