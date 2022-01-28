package br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc.provider

import br.com.ezequiel.travels.infrastructure.repository.passenger.r2dbc.entity.PassengerEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface R2dbcPassengerProvider : R2dbcRepository<PassengerEntity, UUID>