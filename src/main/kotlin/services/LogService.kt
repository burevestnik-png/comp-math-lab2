package services

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class LogService : Controller() {
    var logs = SimpleStringProperty()

    fun print(string: String) = logs.set(getSnapshot() + string)

    fun println(string: String) {
        logs.set(getSnapshot() + string)
        println()
    }

    fun println() = logs.set(getSnapshot() + "\n")

    private fun getSnapshot(): String = logs.get() ?: ""
}