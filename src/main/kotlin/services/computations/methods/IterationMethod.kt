package services.computations.methods

import domain.models.UserInputModel
import services.LogService
import kotlin.math.abs
import services.computations.MathUtils.Companion.computeFunctionByX as f
import services.computations.MathUtils.Companion.findDerivativeByX as df

data class IterationLog(
    val x: Double,
    val nextX: Double,
    val phiNextX: Double,
    val fNextX: Double,
    val condition: Double
) : Log

class IterationMethod : ComputationMethod {
    override val description: String
        get() = "simple iteration method"

    override fun compute(userInputModel: UserInputModel, logService: LogService): List<Log> {
        val logs = mutableListOf<Log>()
        val equation = userInputModel.equation.value

        val a = userInputModel.leftBorder.value
        val b = userInputModel.rightBorder.value

        val derivativeA = df(equation, a)
        val derivativeB = df(equation, b)
        val maxDerivative = sequenceOf(derivativeA, derivativeB).maxOrNull()!!

        var iterations = 0
        val lambda = (-1) / maxDerivative
        var x = a
        do {
            val previousX = x
            x += lambda * f(equation, x)
            iterations++
            logs.add(IterationLog(previousX, x, x + lambda * f(equation, x), f(equation, x), abs(x - previousX)))
        } while (abs(x - previousX) > userInputModel.accuracy.value && iterations < 1000)

        logService.println("Root: $x")
        return logs
    }
}