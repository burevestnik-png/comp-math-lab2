package views

import domain.Equation
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.dao.EquationDAO
import tornadofx.*
import views.fragments.Notification
import views.graphView.GraphView
import views.optionView.OptionView

class RootView : View("Yarki's computations") {
    private val equation = Equation(arrayOf("+", "1.0", "-", "4.5", "-", "9.21", "-", "0.383"), arrayOf("0"))
    private val equationDAO: EquationDAO by inject()

    override val root = borderpane {
        top = menubar {
            menu("Menu") {
                item("Import") {
                    action {
//                       val equation = equationDAO.getItem(Mode.RESOURCE, "/examples/example-1.json")
                        val equation = equationDAO.getItem()
                        if (equation == null) {
                            openInternalWindow<Notification>()
                        }
//                        println(equation)
                    }
                }
                separator()
                item("Save", "Shortcut+S").action {
                    TODO("ADD SHORTCUT")
                    println("Saving!")
                }
                item("Quit", "Shortcut+Q").action {
                    TODO("ADD SHORTCUT")
                    println("Quitting!")
                }
            }
        }

        center = hbox {
            alignment = Pos.CENTER

            vbox {
                hboxConstraints {
                    hgrow = Priority.ALWAYS
                }

                add(find<OptionView>(mapOf(RootView::equation to equation)))
            }

            separator(Orientation.VERTICAL)

            vbox {
                hboxConstraints {
                    hgrow = Priority.ALWAYS
                }

                add(find<GraphView>(mapOf(RootView::equation to equation)))
            }
        }
    }
}
