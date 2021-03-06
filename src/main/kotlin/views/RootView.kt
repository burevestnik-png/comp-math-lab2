package views

import domain.JsonUserInput
import domain.models.UserInputModel
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.dao.JsonDAO
import services.dao.exceptions.NoFileChosenException
import services.utils.Notifications
import tornadofx.*
import views.fragments.Notification
import views.fragments.NotificationType
import views.graphView.GraphView
import views.optionView.OptionView
import java.io.IOException
import java.lang.Exception

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
                item("Save", "Ctrl+S").action {
                    action {
                        saveCurrentAppSnapshot()
                    }
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
            if (userInput.equation != null) equation.value = userInput.equation
        }
    }

    private fun saveCurrentAppSnapshot() {
        val snapshot = with(userInputModel) {
             JsonUserInput(
                leftBorder.value,
                rightBorder.value,
                accuracy.value,
                equation.value
            )
        }

        try {
            jsonUserInputDAO.saveItem(snapshot)
        } catch (e: Exception) {
            when (e) {
                is NoFileChosenException -> {
                    Notifications.info(this, e.message)
                }
                is IOException -> {

                }
            }
        }
    }

    private fun importUserInput(): JsonUserInput? {
        return try {
            jsonUserInputDAO.getItem(JsonUserInput::class.java)
        } catch (e: NoFileChosenException) {
            Notifications.info(this, e.message)
            null
        }
    }
}
