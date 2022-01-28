package br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc.provider

import br.com.ezequiel.travels.infrastructure.repository.driver.r2dbc.entity.DriverEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface R2dbcDriverProvider : R2dbcRepository<DriverEntity, UUID>