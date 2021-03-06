package views.graphView

import domain.Equation
import domain.models.UserInputModel
import javafx.beans.property.Property
import javafx.beans.property.SimpleListProperty
import javafx.collections.ObservableList
import javafx.scene.Cursor
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import services.GraphService
import tornadofx.*
import views.styles.RootStyles
import views.styles.Util

class GraphView : View() {
    private val userInputModel: UserInputModel by inject()
    private val graphService: GraphService by inject()
    private val currentGraph: Property<ObservableList<XYChart.Data<Number, Number>>> = SimpleListProperty()

    init {
        currentGraph.value =
            graphService.getPlotMeta(
                userInputModel.equation.value,
                -10.0,
                10.0,
                0.1
            ) as? ObservableList<XYChart.Data<Number, Number>>?

        with(userInputModel) {
            leftBorder.value = -10.0
            rightBorder.value = 10.0
            accuracy.value = 0.1

            equation.onChange {
                redraw(it, leftBorder.value, rightBorder.value, accuracy.value)
            }

            leftBorder.onChange {
                redraw(equation.value, it, rightBorder.value, accuracy.value)
            }

            rightBorder.onChange {
                redraw(equation.value, leftBorder.value, it, accuracy.value)
            }

            accuracy.onChange {
                if (it in 0.01..1.0) {
                    redraw(equation.value, leftBorder.value, rightBorder.value, it)
                }
            }
        }
    }

    private fun redraw(equation: Equation?, left: Double, right: Double, accuracy: Double) {
        currentGraph.value.clear()
        for (value in graphService.getPlotMeta(equation, left, right, accuracy)!!) {
            currentGraph.value.add(value as XYChart.Data<Number, Number>)
        }
    }

    override val root = vbox {
        linechart("Graph", NumberAxis(-10.0, 10.0, 1.0), NumberAxis(-80.0, 80.0, 1.0)) {
            isLegendVisible = false
            cursor = Cursor.CROSSHAIR

            prefWidth = Util.scale(RootStyles.PREF_WIDTH.toDouble(), 0.6)
            prefHeight = RootStyles.PREF_HEIGHT.toDouble()

            series("Graph", elements = currentGraph.value)
        }
    }

}
