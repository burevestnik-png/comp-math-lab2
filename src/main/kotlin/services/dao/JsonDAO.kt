package services.dao

import com.google.gson.Gson
import javafx.stage.FileChooser
import services.dao.exceptions.NoFileChosenException
import services.utils.Resources
import tornadofx.Controller
import java.io.File
import java.io.PrintWriter

class JsonDAO<T> : DAO<T>, Controller() {
    private val NFCE_MESSAGE_IMPORT = "Please, choose the file which you want to import"
    private val NFCE_MESSAGE_SAVE = "Please, choose the file which you want to save"

    private val fileChooser: FileChooser = FileChooser().apply {
        title = "Select file"
        initialDirectory = File(System.getProperty("user.home"))
        extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
    }


    override fun saveItem(t: T) {
        val fileToSave = fileChooser.showSaveDialog(null) ?: throw NoFileChosenException(NFCE_MESSAGE_SAVE)
        PrintWriter(fileToSave).run {
            println(toJson(t))
            close()
        }
    }

    override fun getAll(clazz: Class<T>, mode: Mode): List<T> {
        val files: MutableList<T> = arrayListOf()

        when (mode) {
            Mode.RESOURCE -> {
                for (res in Resources.values()) {
                    files.add(getItem(clazz, Mode.RESOURCE, res.source))
                }
            }
            Mode.FILE -> {
                TODO("Not yet implemented")
            }
        }

        return files.toList()
    }

    override fun getItem(clazz: Class<T>, mode: Mode, source: String): T {
        return when (mode) {
            Mode.FILE -> {
                val file = fileChooser.showOpenDialog(null) ?: throw NoFileChosenException(NFCE_MESSAGE_IMPORT)
                parseJson(file.readText(), clazz)
            }
            Mode.RESOURCE -> {
                val file = resources.json(source)
                parseJson(file.toString(), clazz)
            }
        }
    }

    private fun toJson(t: T): String = Gson().toJson(t)

    private fun parseJson(content: String, clazz: Class<T>): T = Gson().fromJson(content, clazz)
}