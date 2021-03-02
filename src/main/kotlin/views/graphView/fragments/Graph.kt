package views.graphView.fragments

import javafx.scene.Cursor
import javafx.scene.chart.NumberAxis
import tornadofx.View
import tornadofx.data
import tornadofx.linechart
import tornadofx.series

class Graph : View() {
    val dots: Map<Double, Double> by param()

    override val root = linechart("sosat ne vredno", NumberAxis(-10.0, 10.0, 1.0), NumberAxis(-80.0, 80.0, 1.0)) {
        isLegendVisible = false
        cursor = Cursor.CROSSHAIR

        series("Graph") {
            for (entry in dots.entries) {
                data(entry.key, entry.value)
            }
        }
    }

    fun clear() = root.series("Graph").data.clear()

}
