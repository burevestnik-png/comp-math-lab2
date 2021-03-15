package services.computations.methods

import domain.models.UserInputModel
import services.GraphService
import services.LogService
import kotlin.math.abs
import services.computations.MathUtils.Companion.computeFunctionByX as f
import services.computations.MathUtils.Companion.findDerivativeByX as df

data class TangentLog(
    val x: Double,
    val fx: Double,
    val dfx: Double,
    val nextX: Double,
    val condition: Double
) : Log

class TangentMethod : ComputationMethod {
    override val description: String
        get() = "tangent method"

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
            GraphService.isFirstDerivativeZero(userInputModel) -> {
                logService.printdln("The condition about f'(x) != 0 doesn't performed")
                return emptyList()
            }
            else -> logService.println("The input satisfied all conditions, continue computations...")
        }

        val logs = mutableListOf<Log>()
        val equation = userInputModel.equation.value

        val a = userInputModel.leftBorder.value
        val b = userInputModel.rightBorder.value

        var iterations = 1
        var x = if (f(equation, a) * df(equation, a, 2) > 0) a else b
        var nextX = x - f(equation, x) / df(equation, x)
        logs.add(TangentLog(x, f(equation, x), df(equation, x), nextX, abs(x - nextX)))

        while (abs(x - nextX) > userInputModel.accuracy.value && iterations < 1000) {
            x = nextX
            nextX = x - f(equation, x) / df(equation, x)
            iterations++
            logs.add(TangentLog(x, f(equation, x), df(equation, x), nextX, abs(x - nextX)))
        }

        logService.println("Root: $nextX")
        return logs
    }
}