package domain

import com.google.gson.annotations.SerializedName
import domain.enums.Sign

data class Equation(
    @SerializedName("left-tokens")
    val leftTokens: Array<String>,
    @SerializedName("right-tokens")
    val rightTokens: Array<String>
) {
    constructor(leftTokens: Array<String>) : this(leftTokens, arrayOf("0"))

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

        val size = leftTokens.size
        leftTokens.forEachIndexed { index: Int, token: String ->
            result += when {
                Sign.isSign(token) -> " $token "
                else -> "$token * x^${(size - index) / 2}"
            }
        }

        result += " = "
        rightTokens.forEach { s -> result += s }
        return result
    }
}
