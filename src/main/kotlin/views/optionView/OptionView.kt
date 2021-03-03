package views.optionView

import domain.Equation
import domain.models.UserInputModel
import javafx.collections.FXCollections
import javafx.geometry.Pos
import services.LogService
import tornadofx.*
import views.styles.RootStyles
import views.styles.Util

class OptionView : View() {
    private val equation: Equation by param()
    private val functions = FXCollections.observableArrayList(equation)
    private val userInputModel: UserInputModel by inject()
    private val logService: LogService by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        prefWidth = Util.scale(RootStyles.PREF_WIDTH.toDouble(), 0.1)


        form {
            fieldset("Choose function:") {
                field {
                    combobox<Equation>(userInputModel.equation) {
                        useMaxWidth = true
                        items = functions
                    }
                }
            }

            fieldset("Choose options:") {
                field("Left border:") {
                    textfield(userInputModel.leftBorder)
                }

                field("Right border:") {
                    textfield(userInputModel.rightBorder)
                }

                field("Accuracy:") {
                    textfield(userInputModel.accuracy)
                }
            }

            hbox {
                alignment = Pos.CENTER

                button("Compute") {
                    action {
                        userInputModel.commit {
                            val a = userInputModel.item
                            logService.add(a.equation.toString())
                        }
                    }
                }
            }
        }

        separator()

        form {
            fieldset("Logs:") {
                textarea(logService.logs)
            }
        }

        separator()

        hbox {
            useMaxHeight = true
        }

        imageview("yarki.png") {
            scaleX = .5
            scaleY = .5
        }
    }
}
