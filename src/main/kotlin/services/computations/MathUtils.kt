package services.computations

import domain.Equation
import domain.enums.Sign
import services.Parser.Companion.getInt
import services.Parser.Companion.getInts
import services.Parser.Companion.isCos
import services.Parser.Companion.isExp
import services.Parser.Companion.isSin
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

class MathUtils {
    companion object {
        // Todo try to abstract from @when@ clause for Sign enum
        fun computeFunctionByX(equation: Equation, x: Double): Double {
            var result = 0.0
            var tempSign = Sign.PLUS
            equation.leftTokens.forEach { token ->
                if (Sign.isSign(token)) {
                    tempSign = Sign.identifySign(token)
                } else {
                    result += when (tempSign) {
                        Sign.PLUS -> {
                            when {
                                isSin(token) -> getInts(token)[0] * sin(getInts(token)[1] * x)
                                isExp(token) -> getInts(token)[0] * exp(getInts(token)[1] * x)
                                else -> token.toDouble() *
                                        x.pow((equation.leftTokens.size - equation.leftTokens.indexOf(token)) / 2)
                            }
                        }
                        Sign.MINUS -> {
                            when {
                                isSin(token) -> (-1) * getInts(token)[0] * sin(getInts(token)[1] * x)
                                isExp(token) -> (-1) * getInts(token)[0] * exp(getInts(token)[1] * x)
                                else -> token.toDouble() * (-1) *
                                        x.pow((equation.leftTokens.size - equation.leftTokens.indexOf(token)) / 2)
                            }
                        }
                    }
                }
            }

            return result
        }

        fun findDerivativeByX(equation: Equation, x: Double, derivativePower: Int = 1): Double {
            return when (derivativePower) {
                1 -> findFirstDerivativeByX(equation, x)
                2 -> findSecondDerivativeByX(equation, x)
                else -> TODO(
                    "Unreachable code, probably developer wanted to add enum to avoid this Runtime Error," +
                            "but he has lack of time"
                )
            }
        }

        private fun findFirstDerivativeByX(equation: Equation, x: Double): Double {
            var result = 0.0

            val derivativeTokens = mutableListOf<String>()
            if (isEquationTranscendental(equation)) {
                derivativeTokens.addAll(listOf("+", "1 cos 1", "+", "2 e 2"))
            } else {
                equation.leftTokens.forEachIndexed {
                    index: Int, string: String ->
                }
            }

            return 0.0
        }

        private fun findSecondDerivativeByX(equation: Equation, x: Double): Double {
            return 0.0
        }

        private fun isEquationTranscendental(equation: Equation): Boolean {
            var result = false
            equation.leftTokens.forEach { token -> if (isExp(token) || isSin(token) || isCos(token)) result = true }
            return result
        }
    }
}