package services

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class LogService : Controller() {
    var logs = SimpleStringProperty()
    val fullLogs = SimpleStringProperty()

    fun print(string: String) {
        logs.set(getSnapshot() + string)
        fullLogs.set(getFullSnapshot() + string)
    }

    fun println(string: String) {
        logs.set(getSnapshot() + string)
        fullLogs.set(getFullSnapshot() + string)
        println()
    }

    fun printdln(string: String) {
        println(string)
        println()
    }

    fun println() {
        logs.set(getSnapshot() + "\n")
        fullLogs.set(getFullSnapshot() + "\n")
    }

    fun printlnShadow(string: String) {
        fullLogs.set(getFullSnapshot() + string)
        printlnShadow()
    }

    fun printlnShadow() = fullLogs.set(getFullSnapshot() + "\n")

    private fun getSnapshot(): String = logs.get() ?: ""
    private fun getFullSnapshot(): String = fullLogs.get() ?: ""
}