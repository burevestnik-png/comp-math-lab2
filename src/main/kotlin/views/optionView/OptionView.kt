package views.optionView

import domain.Equation
import domain.models.UserInputModel
import javafx.collections.FXCollections
import javafx.geometry.Pos
import services.LogService
import services.dao.JsonDAO
import services.dao.Mode
import tornadofx.*
import views.styles.RootStyles
import views.styles.Util

class OptionView : View() {
    private val resourceEquations: MutableList<Equation> = arrayListOf()

    private val equationDAO: JsonDAO<Equation> by inject()
    private val userInputModel: UserInputModel by inject()
    private val logService: LogService by inject()

    init {
        resourceEquations.addAll(equationDAO.getAll(Equation::class.java, Mode.RESOURCE))
        userInputModel.equation.value = resourceEquations.first()
    }

    override val root = vbox {
        alignment = Pos.CENTER
        prefWidth = Util.scale(RootStyles.PREF_WIDTH.toDouble(), 0.1)


        form {
            fieldset("Choose function:") {
                field {
                    combobox(userInputModel.equation) {
                        useMaxWidth = true
                        items = FXCollections.observableArrayList(resourceEquations)
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
