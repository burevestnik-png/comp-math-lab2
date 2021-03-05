package views

import domain.Equation
import domain.UserInput
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.dao.JsonDAO
import services.dao.Mode
import services.dao.exceptions.NoFileChosenException
import tornadofx.*
import views.fragments.Notification
import views.fragments.NotificationType
import views.graphView.GraphView
import views.optionView.OptionView

class RootView : View("Yarki's computations") {
    private val equation = Equation(arrayOf("+", "1.0", "-", "4.5", "-", "9.21", "-", "0.383"), arrayOf("0"))
    private val resourceEquations: MutableList<Equation> = arrayListOf()
    private val equationDAO: JsonDAO<Equation> by inject()
    private val userInputDAO: JsonDAO<UserInput> by inject()

    override val root = borderpane {
        top = menubar {
            menu("Menu") {
                item("Import", "Ctrl+I") {
                    action {
                        val userInput = importEquation()
                    }
                }
                separator()
                item("Save", "Ctrl+S").action {
                    println("Saving!")
                }
                item("Quit", "Ctrl+Q").action {
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

                add(find<OptionView>(mapOf(RootView::resourceEquations to resourceEquations)))
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
        resourceEquations.addAll(equationDAO.getAll(Equation::class.java, Mode.RESOURCE))
        resourceEquations.forEach { println(it)}
    }

    private fun importEquation(): UserInput? {
        return try {
            userInputDAO.getItem(UserInput::class.java)
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
