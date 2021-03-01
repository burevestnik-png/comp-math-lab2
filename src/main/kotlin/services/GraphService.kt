package services

import domain.Equation
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
    fun getPlotMeta(equation: Equation, from: Double, to: Double, step: Double): Map<Double, Double> {
        val dots: MutableMap<Double, Double> = emptyMap<Double, Double>().toMutableMap()

        var fromCopy = from
        while (fromCopy <= to) {
            dots[fromCopy] = calculateYByX(equation, fromCopy)
            fromCopy += step
        }

        return dots
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
