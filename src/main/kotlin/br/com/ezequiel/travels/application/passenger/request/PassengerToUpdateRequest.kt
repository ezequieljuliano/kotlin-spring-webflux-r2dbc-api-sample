package br.com.ezequiel.travels.application.passenger.request

import br.com.ezequiel.travels.domain.passenger.model.PassengerToUpdate
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class PassengerToUpdateRequest(

    @field:NotEmpty
    @field:Size(min = 5, max = 255)
    @field:Schema(description = "Passenger name")
    val name: String

)

fun PassengerToUpdateRequest.toModel(id: UUID) = PassengerToUpdate(
    id = id,
    name = name
)
