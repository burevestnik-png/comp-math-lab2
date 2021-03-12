package domain.models

import domain.DrawingMode
import domain.DrawingSettings
import javafx.beans.property.Property
import tornadofx.ItemViewModel

class DrawingSettingsModel : ItemViewModel<DrawingSettings>(DrawingSettings().apply {
    drawingMode = DrawingMode.CLASSIC
}) {
    val drawingMode: Property<DrawingMode> = bind { item?.drawingModeProperty() }
}