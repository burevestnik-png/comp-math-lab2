package domain.models

import domain.Equation
import domain.UserInput
import javafx.beans.property.DoubleProperty
import javafx.beans.property.Property
import services.computations.methods.CompMethodType
import tornadofx.ItemViewModel

class UserInputModel : ItemViewModel<UserInput>(UserInput().apply {
    leftBorder = -5.0
    rightBorder = 5.0
    accuracy = 0.01
    methodType= CompMethodType.CHORD
}) {
    val equation: Property<Equation> = bind { item?.equationProperty() }
    val leftBorder: DoubleProperty = bind { item?.leftBorderProperty() }
    val rightBorder: DoubleProperty = bind { item?.rightBorderProperty() }
    val accuracy: DoubleProperty = bind { item?.accuracyProperty() }
    val methodType: Property<CompMethodType> = bind { item?.methodTypeProperty() }
}