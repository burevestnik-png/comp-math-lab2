package views

import domain.JsonUserInput
import domain.models.UserInputModel
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.dao.JsonDAO
import services.dao.exceptions.NoFileChosenException
import services.utils.Alerts
import tornadofx.*
import views.graphView.GraphView
import views.optionView.OptionView
import java.io.IOException

class RootView : View("Yarki's computations") {
    private val jsonUserInputDAO: JsonDAO<JsonUserInput> by inject()
    private val userInputModel: UserInputModel by inject()

    init {
        userInputModel.apply {
            leftBorder.value = -10.0
            rightBorder.value = 10.0
            accuracy.value = 0.1
        }
    }

    override val root = borderpane {
        top = menubar {
            menu("Menu") {
                item("Import", "Ctrl+I").action {
                    importUserInput()?.let {
                        updateUserInputModel(it)
                    }
                }
                item("Save", "Ctrl+S").action {
                    saveCurrentAppSnapshot()
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
        try {
            jsonUserInputDAO.saveItem(getAppSnapshot())
        } catch (e: Exception) {
            when (e) {
                is NoFileChosenException -> Alerts.error(e.toString(), e.message)
                is IOException -> Alerts.error("Unexpected error", "Saving error!")
            }
        }
    }

    private fun getAppSnapshot(): JsonUserInput {
        return userInputModel.run {
            JsonUserInput(
                leftBorder.value,
                rightBorder.value,
                accuracy.value,
                equation.value
            )
        }
    }

    private fun importUserInput(): JsonUserInput? {
        return try {
            jsonUserInputDAO.getItem(JsonUserInput::class.java)
        } catch (e: NoFileChosenException) {
            Alerts.error(e.toString(), e.message)
            null
        }
    }
}
