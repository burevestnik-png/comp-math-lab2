package services.computations

import domain.Equation
import domain.enums.Sign
import kotlin.math.pow

class Utils {
    companion object {
        fun computeFunctionByX(equation: Equation, x: Double): Double {
            var result = 0.0
            var tempSign = Sign.PLUS
            equation.leftTokens.forEach { token ->
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
}