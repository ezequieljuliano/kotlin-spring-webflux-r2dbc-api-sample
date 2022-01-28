package br.com.ezequiel.travels.domain.driver.service

import br.com.ezequiel.travels.domain.driver.CreateDriver
import br.com.ezequiel.travels.domain.driver.model.DriverToCreate
import br.com.ezequiel.travels.domain.driver.model.toDriver
import br.com.ezequiel.travels.domain.driver.repository.DriverRepository
import org.springframework.stereotype.Service

@Service
class CreateDriverService(private val driverRepository: DriverRepository) : CreateDriver {

    override fun execute(driverToCreate: DriverToCreate) = driverRepository.save(driverToCreate.toDriver())

}