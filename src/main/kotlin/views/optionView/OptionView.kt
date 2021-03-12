package views.optionView

import domain.DrawingMode
import domain.Equation
import domain.models.DrawingSettingsModel
import domain.models.UserInputModel
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import services.LogService
import services.computations.ComputationService
import services.dao.JsonDAO
import services.dao.Mode
import tornadofx.*
import views.styles.RootStyles
import views.styles.Util

class OptionView : View() {
    private val resourceEquations: MutableList<Equation> = arrayListOf()

    private val equationDAO: JsonDAO<Equation> by inject()
    private val userInputModel: UserInputModel by inject()
    private val drawingSettingsModel: DrawingSettingsModel by inject()
    private val computationService: ComputationService by inject()
    private val logService: LogService by inject()

    init {
        resourceEquations.addAll(equationDAO.getAll(Equation::class.java, Mode.RESOURCE))

        userInputModel.apply {
            equation.onChange {
                logService.println("Drawing: $it")
            }
            equation.value = resourceEquations.first()
        }
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
                field("Left border:").textfield(userInputModel.leftBorder)
                field("Right border:").textfield(userInputModel.rightBorder)
                field("Accuracy:").textfield(userInputModel.accuracy)
            }

            hbox {
                alignment = Pos.CENTER

                button("Compute").action {
                    userInputModel.commit {
                        computationService.computeEquation(userInputModel)
                    }
                }
            }

            separator()
            fieldset("Logs:").textarea(logService.logs) {
                isEditable = false
            }

            separator()
            fieldset("Drawing settings:") {
                field("Drawing mode:") {
                    hbox {
                        useMaxWidth = true
                        alignment = Pos.CENTER
                        togglegroup {
                            DrawingMode.values().forEach {
                                radiobutton(it.toString(), value = it) {
                                    prefWidth = 129.0
                                    alignment = Pos.CENTER
                                    hgrow = Priority.ALWAYS
                                }
                            }

                            bind(drawingSettingsModel.drawingMode)
                        }
                    }
                }
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
