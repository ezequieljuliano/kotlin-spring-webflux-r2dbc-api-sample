package br.com.ezequiel.travels.domain.driver.model

import java.time.LocalDate

data class DriverToCreate(

    val name: String,
    val birthdate: LocalDate

)

fun DriverToCreate.toDriver() = Driver(
    id = null,
    name = name,
    birthdate = birthdate
)
