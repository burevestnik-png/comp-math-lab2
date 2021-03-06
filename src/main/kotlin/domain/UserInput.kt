package domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tornadofx.getProperty
import tornadofx.property

data class JsonUserInput(
    @SerializedName("left-border")
    val leftBorder: Double,
    @SerializedName("right-border")
    val rightBorder: Double,
    val accuracy: Double,
    @Expose(deserialize = false)
    val equation: Equation? = Equation(emptyArray(), emptyArray())
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JsonUserInput

        if (leftBorder != other.leftBorder) return false
        if (rightBorder != other.rightBorder) return false
        if (accuracy != other.accuracy) return false
        if (equation != other.equation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leftBorder.hashCode()
        result = 31 * result + rightBorder.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + (equation?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "JsonUserInput(leftBorder=$leftBorder, rightBorder=$rightBorder, accuracy=$accuracy, equation=$equation)"
    }
}

class UserInput(
) {
    var equation: Equation by property<Equation>()
    fun equationProperty() = getProperty(UserInput::equation)

    var leftBorder: Double by property<Double>()
    fun leftBorderProperty() = getProperty(UserInput::leftBorder)

    var rightBorder: Double by property<Double>()
    fun rightBorderProperty() = getProperty(UserInput::rightBorder)

    var accuracy: Double by property<Double>()
    fun accuracyProperty() = getProperty(UserInput::accuracy)

    override fun toString(): String {
        return "UserInput(equation=$equation, leftBorder=$leftBorder, rightBorder=$rightBorder, accuracy=$accuracy)"
    }
}