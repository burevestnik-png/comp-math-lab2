package views.graphView

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

        userInputModel.equation.onChange {
            currentGraph.value.clear()
            for (value in graphService.getPlotMeta(it, -10.0, 10.0, 0.1)!!) {
                currentGraph.value.add(value as XYChart.Data<Number, Number>)
            }
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
