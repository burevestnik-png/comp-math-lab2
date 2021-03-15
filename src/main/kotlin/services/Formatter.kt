package services

import domain.Equation
import domain.enums.Sign
import services.computations.MathUtils

class Formatter {
    companion object {
        fun formatEquation(equation: Equation): String {
            if (!MathUtils.isEquationTranscendental(equation)) {
                return equation.toString()
            } else {
                var result = ""
                equation.leftTokens.forEach { token ->
                    result += if (Sign.isSign(token)) {
                        " $token "
                    } else {
                        val factors = Parser.getInts(token)
                        when {
                            Parser.isSin(token) -> "${factors[0]} * sin(${factors[1]}x)"
                            Parser.isCos(token) -> "${factors[0]} * cos(${factors[1]}x)"
                            Parser.isExp(token) -> "${factors[0]} * e^(${factors[1]}x)"
                            else -> error("Unreachable code")
                        }
                    }
                }
                return result
            }
        }
    }
}