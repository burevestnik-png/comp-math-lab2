package views.graphView

import domain.Equation
import services.GraphService
import tornadofx.View
import tornadofx.vbox
import views.graphView.fragments.Graph

class GraphView : View() {
    private val equation: Equation by param()

    private val graphService: GraphService by inject()

    private val graph = find<Graph>(mapOf(Graph::dots to graphService.getPlotMeta(equation, -2.0, 8.0, 0.1)))

    override val root = vbox {
        add(graph.root)
    }

}
