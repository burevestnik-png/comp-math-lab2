package services.computations.methods

import domain.models.UserInputModel
import services.LogService
import kotlin.math.abs
import kotlin.math.pow
import services.computations.MathUtils.Companion.computeFunctionByX as f
import services.computations.MathUtils.Companion.findDerivativeByX as df

data class IterationLog(
    val x: Double,
    val nextX: Double,
    val phiNextX: Double,
    val fx: Double,
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
        logService.println("A: $derivativeA")
        logService.println("A: $derivativeB")
        val maxDerivative = sequenceOf(derivativeA, derivativeB).maxOrNull()!!

        var iterations = 0
        val lambda = (-1) / maxDerivative
        logService.println("Lambda: $lambda")
        logService.println("|phi'(a)|: ${abs(dphi(a, lambda))}")
        logService.println("|phi'(b)|: ${abs(dphi(b, lambda))}")
        var x = a
        do {
            val previousX = x
            x += lambda * f(equation, x)
            iterations++
            logs.add(IterationLog(previousX, x, x + lambda * f(equation, x), f(equation, x), abs(x - previousX)))
        } while ((abs(x - previousX) > userInputModel.accuracy.value || abs(
                f(
                    equation,
                    x
                )
            ) > userInputModel.accuracy.value
                    ) && iterations < 1000
        )

        return if (x.isNaN()) {
            logService.println("The condition |phi'(x)| < 1 on [a,b] doesn't coverage")
            emptyList()
        } else {
            logService.println("Root: $x")
            logService.println("Iterations: $iterations")
            logService.println("f(root): ${f(equation, x)}")
            logs
        }
    }

    private fun phi(x: Double, l: Double): Double {
        return l * x.pow(3) + x + l * x + 4.0 * l
    }

    private fun dphi(x: Double): Double {
        return (-1.0 / 3.0) * x.pow(2) + 10.0 / 9.0
    }

    private fun dphi(x: Double, l: Double): Double {
        return 3.0 * l * x.pow(2) + 1 + l
    }
}