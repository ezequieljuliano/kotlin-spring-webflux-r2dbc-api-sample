package br.com.ezequiel.travels.domain.driver.model

import java.time.LocalDate
import java.util.UUID

data class DriverToFullUpdate(

    val id: UUID,
    val name: String,
    val birthdate: LocalDate

)
