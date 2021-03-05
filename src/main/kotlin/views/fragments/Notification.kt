package views.fragments

import tornadofx.*

enum class NotificationType() {
    INFO {
        override fun toString(): String {
            return "Information"
        }
    }
}

class Notification : Fragment("Notification") {
    val type: NotificationType by param()
    private val content: String by param()

    override val root = borderpane {
        prefHeight = 150.0
        prefWidth = 250.0

        top = label(type.toString())
        center {
            separator()

            label(content)
        }
    }
}
