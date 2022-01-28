package br.com.ezequiel.travels.domain.driver.service

import br.com.ezequiel.travels.domain.driver.GetDriver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetDriverService(private val driverRepository: DriverRepository) : GetDriver {

    override fun execute(driverId: UUID) = driverRepository.getById(driverId)

}