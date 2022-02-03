package br.com.ezequiel.travels.application.travel.rest

import br.com.ezequiel.travels.application.travel.request.TravelDriverRequest
import br.com.ezequiel.travels.application.travel.request.TravelToCreateRequest
import br.com.ezequiel.travels.application.travel.request.toModel
import br.com.ezequiel.travels.application.travel.response.toOutput
import br.com.ezequiel.travels.domain.travel.AcceptTravel
import br.com.ezequiel.travels.domain.travel.CreateTravel
import br.com.ezequiel.travels.domain.travel.ListAvailableTravels
import br.com.ezequiel.travels.domain.travel.RefuseTravel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/travel-requests")
@Tag(name = "Travel Requests API", description = "Manage travel requests data")
class TravelRequestController(
    private val acceptTravel: AcceptTravel,
    private val createTravel: CreateTravel,
    private val listAvailableTravels: ListAvailableTravels,
    private val refuseTravel: RefuseTravel
) {

    @GetMapping("/available", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @Operation(description = "List all available travel requests")
    fun listAvailableTravels() = listAvailableTravels.execute().map { it.toOutput() }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Create a new travel request")
    fun createTravelRequest(@Valid @RequestBody travelToCreate: TravelToCreateRequest) =
        createTravel.execute(travelToCreate.toModel()).toOutput()

    @PutMapping("/{id}/accept", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(description = "Driver accept a travel request")
    fun acceptTravelRequest(@PathVariable("id") id: UUID, @Valid @RequestBody travelDriver: TravelDriverRequest) {
        acceptTravel.execute(id, travelDriver.driverId)
    }

    @PutMapping("/{id}/refuse")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(description = "Refuse a travel request")
    fun refuseTravelRequest(@PathVariable("id") id: UUID) {
        refuseTravel.execute(id)
    }

}