package br.com.ezequiel.travels.domain.travel.model

import java.util.*

data class Travel(

    val id: UUID?,
    val origin: String,
    val destination: String,
    val passenger: TravelPassenger,
    var status: TravelStatus,
    var driver: TravelDriver?

)
