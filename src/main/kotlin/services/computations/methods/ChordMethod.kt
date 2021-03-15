package services.computations.methods

import domain.models.UserInputModel
import services.LogService
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
        if (!isRootExists(userInputModel)) {
            logService.println("On that section that function doesn't have any roots")
            return emptyList()
        } else logService.println("Due to special condition, function has roots on that section")

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

        logService.println("Infinum: $b")
        return logs
    }

    private fun isRootExists(userInputModel: UserInputModel): Boolean {
        val b = f(userInputModel.equation.value, userInputModel.rightBorder.value)
        val a = f(userInputModel.equation.value, userInputModel.leftBorder.value)
        return a * b < 0
    }
}