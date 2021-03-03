package views.optionView

import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.*
import views.styles.RootStyles
import views.styles.Util

class OptionView : View() {
    private val functions = FXCollections.observableArrayList<String>("First", "Second", "Third")

    override val root = vbox {
        alignment = Pos.CENTER
        prefWidth = Util.scale(RootStyles.PREF_WIDTH.toDouble(), 0.1)

        form {
            fieldset("Choose function:") {
                field {
                    combobox<String> {
                        useMaxWidth = true

                        items = functions
                    }
                }
            }

            fieldset("Choose options:") {
                field("Left border:") {
                    textfield()
                }

                field("Right border:") {
                    textfield()
                }

                field("Accuracy:") {
                    textfield()
                }
            }

            hbox {
                alignment = Pos.CENTER

                button("Compute") {
                    action {
                        println("Hi")
                    }
                }
            }
        }

        separator()

        form {
            fieldset("Logs:") {
                textarea {

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
