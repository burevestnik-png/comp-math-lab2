package services

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class LogService : Controller() {
    var logs = SimpleStringProperty()

    fun add(string: String) {
        logs.set(getSnapshot() + string)
        newLine()
    }

    fun newLine() {
        logs.set(getSnapshot() + "\n")
    }

    private fun getSnapshot(): String {
        return logs.get() ?: ""
    }
}