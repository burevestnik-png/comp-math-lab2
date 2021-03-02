package views.optionView

import javafx.geometry.Pos
import tornadofx.View
import tornadofx.button
import tornadofx.vbox

class OptionView : View() {
    override val root = vbox {
        alignment = Pos.CENTER
        height

        button("hello") { }
    }
}
