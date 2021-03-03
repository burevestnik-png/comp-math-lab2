package domain

import tornadofx.getProperty
import tornadofx.property

class UserInput {
    var equation by property<Equation>()
    fun equationProperty() = getProperty(UserInput::equation)

    var leftBorder by property<Double>()
    fun leftBorderProperty() = getProperty(UserInput::leftBorder)

    var rightBorder by property<Double>()
    fun rightBorderProperty() = getProperty(UserInput::rightBorder)

    var accuracy by property<Double>()
    fun accuracyProperty() = getProperty(UserInput::accuracy)

    var logs by property<String>()
    fun logsProperty() = getProperty(UserInput::logs)
}