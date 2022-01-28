package br.com.ezequiel.travels.application.driver.response

import br.com.ezequiel.travels.domain.driver.model.Driver
import io.swagger.v3.oas.annotations.media.Schema
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.util.*

data class DriverResponse(

    @field:Schema(description = "Driver identifier")
    val id: UUID,

    @field:Schema(description = "Driver name")
    val name: String,

    @field:Schema(description = "Driver birthdate")
    val birthdate: LocalDate

)

fun Driver.toOutput() = DriverResponse(
    id = id!!,
    name = name,
    birthdate = birthdate
)

fun Mono<Driver>.toOutput() = map { DriverResponse(it.id!!, it.name, it.birthdate) }