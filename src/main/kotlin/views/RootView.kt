package views

import domain.Equation
import javafx.scene.layout.Priority
import tornadofx.*
import views.graphView.GraphView
import views.optionView.OptionView

class RootView : View("Yarki's computations") {
    private val equation = Equation(arrayOf("+", "1.0", "-", "4.5", "-", "9.21", "-", "0.383"), arrayOf("0"))

    override val root = borderpane {
        center = hbox {
            vbox {
                hboxConstraints {
                    hgrow = Priority.ALWAYS
                }

                add<OptionView>()
            }

            vbox {
                hboxConstraints {
                    hgrow = Priority.ALWAYS
                }

                add(find<GraphView>(mapOf(RootView::equation to equation)))
            }
        }
    }
}
