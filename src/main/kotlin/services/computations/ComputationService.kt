package services.computations

import domain.models.UserInputModel
import services.LogService
import services.computations.methods.ChordMethod
import services.computations.methods.ComputationMethod
import tornadofx.Controller

class ComputationService : Controller() {
    private val logService: LogService by inject()
    private val availableMethods: List<ComputationMethod> = listOf(
        ChordMethod()
    )

    fun computeEquation(userInputModel: UserInputModel) {
        availableMethods.forEach {
            logService.run {
                println()
                println("Computing extreme right root by: ${it.description}")
            }

            it.compute(userInputModel, logService)
        }
    }
}