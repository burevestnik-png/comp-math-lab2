package services

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class LogService : Controller() {
    var logs = SimpleStringProperty()

    fun add(string: String) {
        val s = logs.get()
        logs.set(s + string)
    }
}