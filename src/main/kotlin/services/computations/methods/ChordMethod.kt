package services.computations.methods

import domain.models.UserInputModel
import services.LogService
import services.computations.MathUtils
import kotlin.math.abs
import services.computations.MathUtils.Companion.computeFunctionByX as f

data class ChordCalcLog(
    val a: Double,
    val b: Double,
    val x: Double,
    val fa: Double,
    val fb: Double,
    val fx: Double,
    val condition: Double
) : CalcLog

class ChordMethod : ComputationMethod {
    override val description: String
        get() = "chord method"

    override fun compute(userInputModel: UserInputModel, logService: LogService): List<CalcLog> {
        when {
            !isRootExists(userInputModel) -> {
                logService.printdln("The condition f(a) * f(b) < 0 doesn't performed")
                return emptyList()
            }
            !isFirstDerivativeSaveSign(userInputModel) -> {
                logService.printdln("The condition about f'(x) saving sign doesn't performed")
                return emptyList()
            }
            !isSecondDerivativeSaveSign(userInputModel) -> {
                logService.printdln("The condition about f\"(x) saving sign doesn't performed")
                return emptyList()
            }
            else -> logService.println("The input satisfied all conditions, continue computations...")
        }

        val logs = mutableListOf<CalcLog>()
        val equation = userInputModel.equation.value

        val a = userInputModel.rightBorder.value
        var b = userInputModel.leftBorder.value
        var previousB = a
        while (abs(previousB - b) > userInputModel.accuracy.value) {
            previousB = b

            val fa = f(equation, a)
            val fb = f(equation, previousB)

            b -= (a - b) * fb / (fa - fb) // aka x
            val fx = f(equation, b)

            logs.add(ChordCalcLog(a, previousB, b, fa, fb, fx, abs(previousB - b)))
        }

        logService.println("Root: $b")
        return logs
    }

    /**
     * Basic check f(a) * f(b) < 0
     */
    private fun isRootExists(userInputModel: UserInputModel): Boolean {
        val b = f(userInputModel.equation.value, userInputModel.rightBorder.value)
        val a = f(userInputModel.equation.value, userInputModel.leftBorder.value)
        return a * b < 0
    }

    /**
     * Check saving sign of f'(x)
     */
    private fun isFirstDerivativeSaveSign(userInputModel: UserInputModel): Boolean {
        return isDerivativeSaveSign(userInputModel, 1)
    }

    /**
     * Check saving sign of f"(x)
     */
    private fun isSecondDerivativeSaveSign(userInputModel: UserInputModel): Boolean {
        return isDerivativeSaveSign(userInputModel, 2)
    }

    private fun isDerivativeSaveSign(userInputModel: UserInputModel, derivativePower: Int): Boolean {
        val equation = userInputModel.equation.value
        val b = userInputModel.rightBorder.value
        val a = userInputModel.leftBorder.value

        val isSignNegative = MathUtils.findDerivativeByX(equation, a, derivativePower) < 0
        val isSignPositive = !isSignNegative
        var from = a
        while (from <= b) {
            val derivativeValue = MathUtils.findDerivativeByX(equation, from, derivativePower)
            if (derivativeValue > 0 == isSignNegative || derivativeValue < 0 == isSignPositive) {
                return false
            }
            from += userInputModel.accuracy.value
        }

        return true
    }
}