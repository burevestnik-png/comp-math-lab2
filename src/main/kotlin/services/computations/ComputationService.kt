package services.computations

import domain.models.UserInputModel
import services.utils.Formatter
import services.LogService
import services.computations.methods.ChordMethod
import domain.enums.CompMethodType
import services.computations.methods.ComputationMethod
import services.computations.methods.IterationMethod
import services.computations.methods.TangentMethod
import tornadofx.Controller

class ComputationService : Controller() {
    private val logService: LogService by inject()
    private val availableMethods: Map<CompMethodType, ComputationMethod> = mapOf(
        CompMethodType.CHORD to ChordMethod(),
        CompMethodType.TANGENT to TangentMethod(),
        CompMethodType.ITERATION to IterationMethod()
    )

    fun computeEquation(userInputModel: UserInputModel) {
        logService.run { println(); println("Solving ${Formatter.formatEquation(userInputModel.equation.value)}") }
        val logs = (availableMethods[userInputModel.methodType.value] ?: error("Unreachable nullptr")).run {
            logService.run {
                println("Computing extreme right root by: $description")
            }
            compute(userInputModel, logService)
        }

        logs.run {
            forEach(::println)
            forEachIndexed { index, log ->
                logService.printlnShadow("($index) $log")
            }
        }
    }
}