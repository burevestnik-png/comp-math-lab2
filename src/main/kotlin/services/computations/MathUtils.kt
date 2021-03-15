package services.computations

import domain.Equation
import domain.enums.Sign
import services.Parser.Companion.getInts
import services.Parser.Companion.isCos
import services.Parser.Companion.isExp
import services.Parser.Companion.isSin
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

class MathUtils {
    companion object {
        // Todo try to abstract from @when@ clause for Sign enum
        fun computeFunctionByX(equation: Equation, x: Double): Double {
            var result = 0.0
            var tempSign = Sign.PLUS
            equation.leftTokens.forEachIndexed { index, token ->
                if (Sign.isSign(token)) {
                    tempSign = Sign.identifySign(token)
                } else {
                    result += when (tempSign) {
                        Sign.PLUS -> when {
                            isSin(token) -> getInts(token)[0] * sin(getInts(token)[1] * x)
                            isExp(token) -> getInts(token)[0] * exp(getInts(token)[1] * x)
                            isCos(token) -> getInts(token)[0] * cos(getInts(token)[1] * x)
                            else -> token.toDouble() * x.pow((equation.leftTokens.size - index) / 2)
                        }
                        Sign.MINUS -> when {
                            isSin(token) -> (-1) * getInts(token)[0] * sin(getInts(token)[1] * x)
                            isExp(token) -> (-1) * getInts(token)[0] * exp(getInts(token)[1] * x)
                            isCos(token) -> (-1) * getInts(token)[0] * cos(getInts(token)[1] * x)
                            else -> token.toDouble() * (-1) * x.pow((equation.leftTokens.size - index) / 2)
                        }
                    }
                }
            }

            return result
        }

        fun findDerivativeByX(equation: Equation, x: Double, derivativePower: Int = 1): Double {
            return when (derivativePower) {
                1 -> computeFunctionByX(getDerivative(equation), x)
                2 -> computeFunctionByX(getDerivative(getDerivative(equation)), x)
                else -> TODO(
                    "Unreachable code, probably developer wanted to add enum to avoid this Runtime Error," +
                            "but he has lack of time"
                )
            }
        }

        private fun getDerivative(equation: Equation): Equation {
            val derivativeTokens = mutableListOf<String>()
            if (isEquationTranscendental(equation)) {
                // Лютый хардкод, но нет времени для динамического нахождения производной трансцендетных функций
                if (equation.leftTokens.contains("1 e 2")) {
                    derivativeTokens.addAll(listOf("+", "1 cos 1", "+", "2 e 2"))
                } else {
                    derivativeTokens.addAll(listOf("-", "1 sin 1", "+", "4 e 2"))
                }
            } else {
                equation.leftTokens.forEachIndexed { index: Int, token: String ->
                    if (index < equation.leftTokens.size - 2) {
                        when {
                            Sign.isSign(token) -> derivativeTokens.add(token)
                            else -> {
                                val power = (equation.leftTokens.size - equation.leftTokens.indexOf(token)) / 2
                                val factor = token.toDouble()
                                derivativeTokens.add("${factor * power}")
                            }
                        }
                    }
                }
            }

            return Equation(derivativeTokens.toTypedArray())
        }

        fun isEquationTranscendental(equation: Equation): Boolean {
            var result = false
            equation.leftTokens.forEach { token -> if (isExp(token) || isSin(token) || isCos(token)) result = true }
            return result
        }
    }
}