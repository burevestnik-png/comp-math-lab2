package views

import domain.Equation
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import tornadofx.*
import views.graphView.GraphView
import views.optionView.OptionView

class RootView : View("Yarki's computations") {
    private val equation = Equation(arrayOf("+", "1.0", "-", "4.5", "-", "9.21", "-", "0.383"), arrayOf("0"))

    override val root = borderpane {
        top = menubar {
            menu("Menu") {
                item("Import") {
                    action {
                        val file = FileChooser().showOpenDialog(null)
                    }
                }
                separator()
                item("Save","Shortcut+S").action {
                    println("Saving!")
                }
                item("Quit","Shortcut+Q").action {
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

                add<OptionView>()
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
