package views

import domain.Equation
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.dao.EquationDAO
import services.dao.exceptions.NoFileChosenException
import tornadofx.*
import views.fragments.Notification
import views.fragments.NotificationType
import views.graphView.GraphView
import views.optionView.OptionView

class RootView : View("Yarki's computations") {
    private val equation = Equation(arrayOf("+", "1.0", "-", "4.5", "-", "9.21", "-", "0.383"), arrayOf("0"))
    private val resourceEquations: Array<Equation> = emptyArray()
    private val equationDAO: EquationDAO by inject()

    override val root = borderpane {
        top = menubar {
            menu("Menu") {
                item("Import", "Ctrl+I") {
                    action {
                        val equation = importEquation()
                    }
                }
                separator()
                item("Save", "Ctrl+S").action {
                    TODO("ADD SHORTCUT")
                    println("Saving!")
                }
                item("Quit", "Ctrl+Q").action {
                    /*TODO("ADD SHORTCUT")*/
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

    override fun onDock() {

    }

    private fun importEquation(): Equation? {
        return try {
            equationDAO.getItem()
        } catch (e: NoFileChosenException) {
            openInternalWindow(
                Notification::class,
                params = mapOf(
                    Notification::type to NotificationType.INFO,
                    "content" to e.message
                )
            )
            null
        }
    }
}
