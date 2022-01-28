package br.com.ezequiel.travels.domain.driver.model

import java.time.LocalDate
import java.util.UUID

data class Driver(

    val id: UUID?,
    val name: String,
    val birthdate: LocalDate

)
