package services

import domain.Equation
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import tornadofx.Controller
import kotlin.math.pow

private enum class Sign(val content: String) {
    PLUS("+"),
    MINUS("-");

    companion object {
        fun identifySign(content: String): Sign {
            return when (content) {
                "-" -> MINUS
                "+" -> PLUS
                else -> throw RuntimeException("Internal error")
            }
        }

        fun isSign(content: String): Boolean {
            return content == "-" || content == "+"
        }
    }
}

class GraphService : Controller() {
    fun getPlotMeta(equation: Equation?, from: Double, to: Double, step: Double): ObservableList<XYChart.Data<Double, Double>>? {
        if (equation == null) return null

        val dots: MutableMap<Double, Double> = emptyMap<Double, Double>().toMutableMap()

        var fromCopy = from
        while (fromCopy <= to) {
            dots[fromCopy] = calculateYByX(equation, fromCopy)
            fromCopy += step
        }

        return toFormat(dots)
    }

    private fun toFormat(dots: MutableMap<Double, Double>): ObservableList<XYChart.Data<Double, Double>> {
        val data: MutableList<XYChart.Data<Double, Double>> = arrayListOf()

        for (entry in dots.entries) {
            data.add(XYChart.Data<Double, Double>(entry.key, entry.value))
        }

        return FXCollections.observableArrayList(data)
    }

    private fun calculateYByX(equation: Equation, x: Double): Double {
        var result = 0.0

        var tempSign = Sign.PLUS
        for (token in equation.leftTokens) {
            if (Sign.isSign(token)) {
                tempSign = Sign.identifySign(token)
            } else {
                result += when (tempSign) {
                    Sign.PLUS -> token.toDouble() *
                            x.pow((equation.leftTokens.size - equation.leftTokens.indexOf(token)) / 2)
                    Sign.MINUS -> token.toDouble() * (-1) *
                            x.pow((equation.leftTokens.size - equation.leftTokens.indexOf(token)) / 2)
                }
            }
        }

        return result
    }
}
