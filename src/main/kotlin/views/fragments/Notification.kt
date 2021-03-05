package views.fragments

import tornadofx.*

enum class NotificationType(val abbr: String) {
    INFO("Information")
}

class Notification(type: NotificationType) : Fragment(type.abbr) {
    override val root = borderpane {
        top = label("E")
        center {
            label("Sos")
        }
    }
}
