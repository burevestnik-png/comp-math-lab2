package domain

import com.google.gson.annotations.SerializedName
import tornadofx.getProperty
import tornadofx.property

class UserInput(
) {
    constructor(
        leftBorder: Double,
        rightBorder: Double,
        accuracy: Double,
        equation: Equation?
    ) : this() {
        this.equation = equation ?: Equation(emptyArray(), emptyArray())
        this.accuracy = accuracy
        this.leftBorder = leftBorder
        this.rightBorder = rightBorder
    }


    var equation: Equation by property<Equation>()
    fun equationProperty() = getProperty(UserInput::equation)

    @get:SerializedName("left-border")
    var leftBorder: Double by property<Double>()
    fun leftBorderProperty() = getProperty(UserInput::leftBorder)

    @get:SerializedName("right-border")
    var rightBorder: Double by property<Double>()
    fun rightBorderProperty() = getProperty(UserInput::rightBorder)

    var accuracy: Double by property<Double>()
    fun accuracyProperty() = getProperty(UserInput::accuracy)

    override fun toString(): String {
        return "UserInput(equation=$equation, leftBorder=$leftBorder, rightBorder=$rightBorder, accuracy=$accuracy)"
    }
}