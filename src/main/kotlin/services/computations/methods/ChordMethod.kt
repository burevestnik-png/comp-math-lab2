package services.computations.methods

import domain.models.UserInputModel
import services.LogService
import kotlin.math.abs
import services.computations.MathUtils.Companion.computeFunctionByX as f

class ChordMethod : ComputationMethod {
    override val description: String
        get() = "chord method"

    override fun compute(userInputModel: UserInputModel, logService: LogService) {
        if (!isRootExists(userInputModel)) {
            logService.println("On that section that function doesn't have any roots")
            return
        } else logService.println("Due to special condition, function has roots on that section")

        val supremum = userInputModel.rightBorder.value
        var infinum = userInputModel.leftBorder.value
        var prevInfinum = supremum
        while (abs(prevInfinum - infinum) > userInputModel.accuracy.value) {
            prevInfinum = infinum
            infinum -= (supremum - infinum) * f(
                userInputModel.equation.value,
                infinum
            ) / (f(userInputModel.equation.value, supremum) - f(
                userInputModel.equation.value,
                infinum
            ))
        }

        logService.println("Supremum: $infinum")
    }

    private fun isRootExists(userInputModel: UserInputModel): Boolean {
        val b = f(userInputModel.equation.value, userInputModel.rightBorder.value)
        val a = f(userInputModel.equation.value, userInputModel.leftBorder.value)
        return a * b < 0
    }
}