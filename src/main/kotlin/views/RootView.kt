package views

import domain.Equation
import tornadofx.View
import tornadofx.borderpane
import tornadofx.center
import views.graphView.GraphView

class RootView : View("My View") {
    private val equation = Equation(arrayOf("+", "1.0", "-", "4.5", "-", "9.21", "-", "0.383"), arrayOf("0"))


    override val root = borderpane {
        center {
            add(find<GraphView>(mapOf(RootView::equation to equation)).root)
        }
    }
}
