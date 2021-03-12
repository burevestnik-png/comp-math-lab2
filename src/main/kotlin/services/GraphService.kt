package services

import domain.Equation
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import services.computations.Utils.Companion.computeFunctionByX
import tornadofx.Controller
import tornadofx.toObservable

class GraphService : Controller() {
    fun getPlotMeta(
        equation: Equation?,
        from: Double,
        to: Double,
        step: Double
    ): ObservableList<XYChart.Data<Double, Double>>? {
        if (equation == null) return null

        val dots: MutableMap<Double, Double> = hashMapOf()
//        val dots: MutableMap<Double, Double> = linkedMapOf()

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
