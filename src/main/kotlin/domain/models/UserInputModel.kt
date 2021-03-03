package domain.models

import domain.Equation
import domain.UserInput
import javafx.beans.property.DoubleProperty
import javafx.beans.property.Property
import tornadofx.ItemViewModel

class UserInputModel : ItemViewModel<UserInput>(UserInput()) {
    val equation: Property<Equation> = bind { item?.equationProperty() }
    val leftBorder: DoubleProperty = bind { item?.leftBorderProperty() }
    val rightBorder: DoubleProperty = bind { item?.rightBorderProperty() }
    val accuracy: DoubleProperty = bind { item?.accuracyProperty() }
}