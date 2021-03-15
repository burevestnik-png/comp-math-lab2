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
import services.utils.Alerts
import tornadofx.*
import views.styles.RootStyles
import views.styles.Util
import java.math.RoundingMode

class GraphView : View() {
    private val userInputModel: UserInputModel by inject()
    private val graphService: GraphService by inject()
    private val currentGraph: Property<ObservableList<XYChart.Data<Number, Number>>> = SimpleListProperty()

    init {
        currentGraph.value =
            graphService.getPlotMeta(
                userInputModel.equation.value,
                userInputModel.leftBorder.value,
                userInputModel.rightBorder.value,
                userInputModel.accuracy.value
            ) as? ObservableList<XYChart.Data<Number, Number>>?

        userInputModel.apply {
            equation.onChange {
                redraw(it, leftBorder.value, rightBorder.value, accuracy.value)
                userInputModel.apply {
                    leftBorder.value = -5.0
                    rightBorder.value = 5.0
                    accuracy.value = 0.01
                }
            }

            leftBorder.onChange {
                if (it < rightBorder.value) {
                    redraw(equation.value, it, rightBorder.value, accuracy.value)
                } else {
                    Alerts.error("Input error", "Left border can't be greater than right!")
                }
            }

            rightBorder.onChange {
                if (it > leftBorder.value || it == 0.0) {
                    redraw(equation.value, leftBorder.value, it, accuracy.value)
                } else {
                    Alerts.error("Input error", "Right border can't be smaller than left!")
                }
            }

            accuracy.onChange {
                val rounded = it.toBigDecimal().setScale(3, RoundingMode.CEILING).toDouble()
                if (rounded in 0.01..1.0) {
                    redraw(equation.value, leftBorder.value, rightBorder.value, rounded)
                } else if (rounded != 0.0) {
                    Alerts.info("Accuracy", "Accuracy values", "Values can be from 0,01 to 1,0")
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
        linechart("Graph", NumberAxis(-7.0, 7.0, 1.0), NumberAxis(-50.0, 50.0, 1.0)) {
            isLegendVisible = false
            cursor = Cursor.CROSSHAIR

            prefWidth = Util.scale(RootStyles.PREF_WIDTH.toDouble(), 0.6)
            prefHeight = RootStyles.PREF_HEIGHT.toDouble()

            series("Graph", elements = currentGraph.value)
        }
    }

}
