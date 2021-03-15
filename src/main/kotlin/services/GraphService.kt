package services

import domain.DrawingMode
import domain.Equation
import domain.models.DrawingSettingsModel
import domain.models.UserInputModel
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import services.computations.MathUtils
import services.computations.MathUtils.Companion.computeFunctionByX
import tornadofx.Controller
import tornadofx.toObservable

class GraphService : Controller() {
    private val drawingSettingsModel: DrawingSettingsModel by inject()

    fun getPlotMeta(
        equation: Equation?,
        from: Double,
        to: Double,
        step: Double
    ): ObservableList<XYChart.Data<Double, Double>>? {
        if (equation == null) return null

        val dots: MutableMap<Double, Double> = when (drawingSettingsModel.drawingMode.value) {
            DrawingMode.CLASSIC -> linkedMapOf()
            DrawingMode.CHAOTIC -> hashMapOf()
        }

        var fromCopy = from
        while (fromCopy <= to) {
            dots[fromCopy] = computeFunctionByX(equation, fromCopy)
            fromCopy += step
        }

        return dots.toList().map { pair: Pair<Double, Double> ->
            XYChart.Data(pair.first, pair.second)
        }.toObservable()
    }

    companion object {
        /**
         * Basic check f(a) * f(b) < 0
         */
        fun isRootExists(userInputModel: UserInputModel): Boolean {
            val b = computeFunctionByX(userInputModel.equation.value, userInputModel.rightBorder.value)
            val a = computeFunctionByX(userInputModel.equation.value, userInputModel.leftBorder.value)
            return a * b < 0
        }

        /**
         * Check saving sign of f'(x)
         */
        fun isFirstDerivativeSaveSign(userInputModel: UserInputModel): Boolean {
            return isDerivativeSaveSign(userInputModel, 1)
        }

        /**
         * Check saving sign of f"(x)
         */
        fun isSecondDerivativeSaveSign(userInputModel: UserInputModel): Boolean {
            return isDerivativeSaveSign(userInputModel, 2)
        }

        /**
         * Checks f'(x) == 0 on interval
         */
        fun isFirstDerivativeZero(userInputModel: UserInputModel): Boolean {
            val equation = userInputModel.equation.value
            val b = userInputModel.rightBorder.value
            val a = userInputModel.leftBorder.value

            var from = a
            while (from <= b) {
                if (MathUtils.findDerivativeByX(equation, from) == 0.0) {
                    return true
                }
                from += userInputModel.accuracy.value
            }

            return false
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
}
