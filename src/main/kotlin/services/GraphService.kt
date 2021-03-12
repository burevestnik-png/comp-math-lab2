package services

import domain.DrawingMode
import domain.Equation
import domain.models.DrawingSettingsModel
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import services.computations.MathUtils.Companion.computeFunctionByX
import tornadofx.Controller
import tornadofx.toObservable

class GraphService : Controller() {
    private val drawingSettingsModel: DrawingSettingsModel by inject()

    fun getPlotMeta(
        equation: Equation?,
        from: Double,
        to: Double,
        step: Double
    ): ObservableList<XYChart.Data<Double, Double>>? {
        if (equation == null) return null

        val dots: MutableMap<Double, Double> = when (drawingSettingsModel.drawingMode.value) {
            DrawingMode.CLASSIC -> linkedMapOf()
            DrawingMode.CHAOTIC -> hashMapOf()
        }

        var fromCopy = from
        while (fromCopy <= to) {
            dots[fromCopy] = computeFunctionByX(equation, fromCopy)
            fromCopy += step
        }

        return dots.toList().map { pair: Pair<Double, Double> ->
            XYChart.Data(pair.first, pair.second)
        }.toObservable()
    }
}
