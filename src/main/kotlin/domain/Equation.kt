package domain

data class Equation(
    val leftTokens: Array<String>,
    val rightTokens: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Equation

        if (!leftTokens.contentEquals(other.leftTokens)) return false
        if (!rightTokens.contentEquals(other.rightTokens)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leftTokens.contentHashCode()
        result = 31 * result + rightTokens.contentHashCode()
        return result
    }

    override fun toString(): String {
        var result = ""

        for (token in leftTokens) {
            result += token
        }
        result += "="
        for (token in rightTokens) {
            result += token
        }

        return result
    }
}
