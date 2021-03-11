package services.utils

import javafx.scene.control.Alert

class Alerts {
    companion object {
        fun info(title: String? = "", header: String, content: String? = "") {
            createAlert(Alert.AlertType.INFORMATION, header, content, title).run {
                showAndWait()
            }
        }

        fun warn(title: String? = "", header: String, content: String? = "") {
            createAlert(Alert.AlertType.WARNING, header, content, title).run {
                showAndWait()
            }
        }

        fun error(title: String? = "", header: String? = "", content: String? = "") {
            createAlert(Alert.AlertType.ERROR, header, content, title).run {
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