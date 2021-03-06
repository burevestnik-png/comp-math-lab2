package services.dao

import com.google.gson.Gson
import domain.Equation
import domain.UserInput
import javafx.stage.FileChooser
import org.hildan.fxgson.FxGson
import services.dao.exceptions.NoFileChosenException
import services.utils.Resources
import tornadofx.Controller
import java.io.File

class JsonDAO<T> : DAO<T>, Controller() {
    private val NFCE_MESSAGE = "Please, choose the file which you want to import"

    private val fileChooser: FileChooser = FileChooser().apply {
        title = "Select file"
        initialDirectory = File(System.getProperty("user.home"))
        extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
    }


    override fun saveItem(clazz: Class<T>, source: String, t: T) {
        TODO("Not yet implemented")
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
                val file = fileChooser.showOpenDialog(null) ?: throw NoFileChosenException(NFCE_MESSAGE)
                parseJson(file.readText(), clazz)
            }
            Mode.RESOURCE -> {
                val file = resources.json(source)
                parseJson(file.toString(), clazz)
            }
        }
    }

    private fun parseJson(content: String, clazz: Class<T>): T {
        return Gson().fromJson(content, clazz)
    }
}