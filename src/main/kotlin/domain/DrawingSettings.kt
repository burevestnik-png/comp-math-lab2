package domain

import tornadofx.getProperty
import tornadofx.property

enum class DrawingMode {
    CLASSIC {
        override fun toString() = "Classic"
    },
    CHAOTIC {
        override fun toString() = "Chaotic"
    }
}

class DrawingSettings() {
    var drawingMode: DrawingMode by property<DrawingMode>()
    fun drawingModeProperty() = getProperty(DrawingSettings::drawingMode)
}