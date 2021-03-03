package domain

import javafx.beans.property.DoubleProperty
import javafx.beans.property.Property
import javafx.beans.property.StringProperty
import tornadofx.ItemViewModel

class UserInputModel : ItemViewModel<UserInput>(UserInput()) {
    val equation: Property<Equation> = bind { item?.equationProperty() }
    val leftBorder: DoubleProperty = bind { item?.leftBorderProperty() }
    val rightBorder: DoubleProperty = bind { item?.rightBorderProperty() }
    val accuracy: DoubleProperty = bind { item?.accuracyProperty() }
    val logs: StringProperty = bind { item?.logsProperty() }
}