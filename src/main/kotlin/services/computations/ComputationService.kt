package services.computations

import domain.models.UserInputModel
import services.Formatter
import services.LogService
import services.computations.methods.ChordMethod
import services.computations.methods.CompMethodType
import services.computations.methods.ComputationMethod
import tornadofx.Controller

class ComputationService : Controller() {
    private val logService: LogService by inject()
    private val availableMethods: Map<CompMethodType, ComputationMethod> = mapOf(
        CompMethodType.CHORD to ChordMethod()
    )

    fun computeEquation(userInputModel: UserInputModel) {
        logService.run { println(); println("Solving ${Formatter.formatEquation(userInputModel.equation.value)}") }
        val logs = (availableMethods[userInputModel.methodType.value] ?: error("Unreachable nullptr")).run {
            logService.run {
                println("Computing extreme right root by: $description")
            }
            compute(userInputModel, logService)
        }

        logService.run {
            logs.forEach(::println)
        }
    }
}