package services.utils

import javafx.scene.control.Alert

class Alerts {
    companion object {
        fun info(header: String, content: String? = "", title: String? = "") {
            createAlert(Alert.AlertType.INFORMATION, header, content, title).apply {
                showAndWait()
            }
        }

        fun warn(header: String, content: String? = "", title: String? = "") {
            createAlert(Alert.AlertType.WARNING, header, content, title).apply {
                showAndWait()
            }
        }

        fun error(header: String? = "", content: String? = "", title: String? = "") {
            createAlert(Alert.AlertType.ERROR, header, content, title).apply {
                showAndWait()
            }
        }

        private fun createAlert(type: Alert.AlertType, header: String? = "", content: String? = "", title: String? = ""): Alert {
            return Alert(type).apply {
                this.title = title
                headerText = header
                contentText = content
            }
        }
    }
}