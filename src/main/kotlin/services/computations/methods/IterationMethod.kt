package services.computations.methods

import domain.Equation
import domain.models.UserInputModel
import services.LogService
import kotlin.math.abs
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

        val derivativeA = df(equation, a).also { logService.println("Derivative in a: $it") }
        val derivativeB = df(equation, b).also { logService.println("Derivative in b: $it") }
        val maxDerivative = sequenceOf(derivativeA, derivativeB).maxOrNull()!!

        var iterations = 0
        val lambda = (-1) / maxDerivative
        logService.println("Lambda: $lambda")

        val dphiA = abs(dphi(a, lambda, equation))
        val dphiB = abs(dphi(b, lambda, equation))

        when {
            dphiA >= 1 -> {
                logService.println("The convergence condition |phi'(a)| < 1 is not satisfied (|phi'(a)| = $dphiA)")
                return emptyList()
            }
            dphiB >= 1 -> {
                logService.println("The convergence condition |phi'(b)| < 1 is not satisfied (|phi'(b)| = $dphiB)")
                return emptyList()
            }
        }

        logService.run {
            println("|phi'(a)| = $dphiA")
            println("|phi'(b)| = $dphiB")
        }

        var x = a
        do {
            val previousX = x
            x += lambda * f(equation, x)
            iterations++
            logs.add(IterationLog(previousX, x, x + lambda * f(equation, x), f(equation, x), abs(x - previousX)))
        } while ((abs(x - previousX) > userInputModel.accuracy.value || abs(f(equation, x))
                    > userInputModel.accuracy.value) && iterations < 1000)

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

    private fun dphi(x: Double, l: Double, equation: Equation): Double {
        return 1.0 + l * df(equation, x)
    }
}