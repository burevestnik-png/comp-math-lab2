package services.computations.methods

import domain.models.UserInputModel
import services.GraphService
import services.LogService
import kotlin.math.abs
import services.computations.MathUtils.Companion.computeFunctionByX as f
import services.computations.MathUtils.Companion.findDerivativeByX as df

data class FixedChordLog(
    val a: Double,
    val b: Double,
    val x: Double,
    val fa: Double,
    val fb: Double,
    val fx: Double,
    val condition: Double
) : Log

data class FloatChordLog(
    val a: Double,
    val b: Double,
    var x: Double,
    var newA: Double,
    var newB: Double,
    var firstCondition: Double,
    var secondCondition: Double
) : Log {
    constructor(a: Double, b: Double) : this(a, b, 0.0, a, b, 0.0, 0.0)
}

class ChordMethod : ComputationMethod {
    override val description: String
        get() = "chord method"

    override fun compute(userInputModel: UserInputModel, logService: LogService): List<Log> {
        when {
            !GraphService.isRootExists(userInputModel) -> {
                logService.printdln("The condition f(a) * f(b) < 0 doesn't performed")
                return emptyList()
            }
            !GraphService.isFirstDerivativeSaveSign(userInputModel) -> {
                logService.printdln("The condition about f'(x) saving sign doesn't performed")
                return emptyList()
            }
            !GraphService.isSecondDerivativeSaveSign(userInputModel) -> {
                logService.printdln("The condition about f\"(x) saving sign doesn't performed")
                return emptyList()
            }
            else -> logService.println("The input satisfied all conditions, continue computations...")
        }

        val logs = mutableListOf<Log>()
        val equation = userInputModel.equation.value

        val a = userInputModel.leftBorder.value
        val b = userInputModel.rightBorder.value

        var guess = 0.0
        var iterations = 0
        if (f(equation, a) * df(equation, a, 2) > 0) {
            // -4 -> -1
            guess = a
            val fb = f(equation, b)
            do {
                val previousGuess = guess
                val fGuess = f(equation, previousGuess)

                guess -= (b - guess) * fGuess / (fb - fGuess) // aka x
                val fx = f(equation, guess)

                logs.add(FixedChordLog(previousGuess, b, guess, fGuess, fb, fx, abs(previousGuess - guess)))
                iterations++
            } while (abs(previousGuess - guess) > userInputModel.accuracy.value && iterations < 1000)
        } else if (f(equation, b) * df(equation, b, 2) > 0) {
            // -0,4 -> 1
            guess = b
            val fa = f(equation, a)
            do {
                val previousGuess = guess
                val fGuess = f(equation, previousGuess)

                guess -= (a - guess) * fGuess / (fa - fGuess) // aka x
                val fx = f(equation, guess)

                logs.add(FixedChordLog(a, previousGuess, guess, fa, fGuess, fx, abs(previousGuess - guess)))
                iterations++
            } while (abs(previousGuess - guess) > userInputModel.accuracy.value && iterations < 1000)
        }

        if (iterations >= 1000) {
            //3 -> 7
            logs.clear()

            var a = a
            var b = b
            var x: Double
            do {
                val log = FloatChordLog(a, b)

                x = (a * f(equation, b) - b * f(equation, a)) / (f(equation, b) - f(equation, a))
                log.x = x

                if (f(equation, a) * f(equation, x) > 0) {
                    a = x
                    log.newA = a
                } else {
                    b = x
                    log.newB = b
                }

                log.apply {
                    firstCondition = abs(b - a)
                    secondCondition = abs(f(equation, x))
                    logs.add(this)
                }
            } while (abs(b - a) > userInputModel.accuracy.value && abs(f(equation, x)) > userInputModel.accuracy.value)

            guess = x
        }

        logService.println("Iterations: $iterations")
        logService.println("Root: $guess")
        return logs
    }
}