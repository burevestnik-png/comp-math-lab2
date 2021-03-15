package services.dao

import javafx.stage.FileChooser
import services.dao.exceptions.NoFileChosenException
import tornadofx.Controller
import java.io.File
import java.io.PrintWriter

class FileDAO<T>: DAO<T>, Controller() {
    private val NFCE_MESSAGE_SAVE = "Please, choose the file which you want to save"

    private val fileChooser: FileChooser = FileChooser().apply {
        title = "Save file"
        initialDirectory = File(System.getProperty("user.home"))
        extensionFilters.add(FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"))
    }

    override fun getAll(clazz: Class<T>, mode: Mode): List<T> {
        error("Unused")
    }

    override fun getItem(clazz: Class<T>, mode: Mode, source: String): T {
        error("Unused")
    }

    override fun saveItem(t: T) {
        val fileToSave = fileChooser.showSaveDialog(null) ?: throw NoFileChosenException(NFCE_MESSAGE_SAVE)
        PrintWriter(fileToSave).run {
            println(t)
            close()
        }
    }
}