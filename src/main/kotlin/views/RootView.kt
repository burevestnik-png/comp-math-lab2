package views

import domain.JsonUserInput
import domain.models.UserInputModel
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.dao.JsonDAO
import services.dao.exceptions.NoFileChosenException
import tornadofx.*
import views.fragments.Notification
import views.fragments.NotificationType
import views.graphView.GraphView
import views.optionView.OptionView

class RootView : View("Yarki's computations") {
    private val jsonUserInputDAO: JsonDAO<JsonUserInput> by inject()
    private val userInputModel: UserInputModel by inject()

    override val root = borderpane {
        top = menubar {
            menu("Menu") {
                item("Import", "Ctrl+I") {
                    action {
                        importUserInput()?.let {
                            updateUserInputModel(it)
                        }
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

                add(find<OptionView>())
            }

            separator(Orientation.VERTICAL)

            vbox {
                hboxConstraints {
                    hgrow = Priority.ALWAYS
                }

                add(find<GraphView>())
            }
        }
    }

    private fun updateUserInputModel(userInput: JsonUserInput) {
        with(userInputModel) {
            leftBorder.value = userInput.leftBorder
            rightBorder.value = userInput.rightBorder
            accuracy.value = userInput.accuracy
        }
    }

    private fun importUserInput(): JsonUserInput? {
        return try {
            jsonUserInputDAO.getItem(JsonUserInput::class.java)
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
