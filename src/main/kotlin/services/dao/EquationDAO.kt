package services.dao

import com.google.gson.Gson
import domain.Equation
import javafx.stage.FileChooser
import services.dao.exceptions.NoFileChosenException
import services.utils.Resources
import tornadofx.Controller
import java.io.File

class EquationDAO : DAO<Equation>, Controller() {
    private val NFCE_MESSAGE = "Please, choose the file which you want to import"

    private val fileChooser: FileChooser = FileChooser().apply {
        title = "Select file"
        initialDirectory = File(System.getProperty("user.home"))
        extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
    }


    override fun saveItem(source: String, t: Equation) {
        TODO("Not yet implemented")
    }

    override fun getAll(mode: Mode): Array<Equation> {
        val files: ArrayList<Equation> = arrayListOf()

        when (mode) {
            Mode.RESOURCE -> {
                for (res in Resources.values()) {
                    getItem(Mode.RESOURCE, res.source).let { files.add(it) }
                }
            }
            Mode.FILE -> {
                TODO("Not yet implemented")
            }
        }

        return files.toTypedArray()
    }

    override fun getItem(mode: Mode, source: String): Equation {
        return when (mode) {
            Mode.FILE -> {
                val file = fileChooser.showOpenDialog(null) ?: throw NoFileChosenException(NFCE_MESSAGE)
                parseJson(file.readText())
            }
            Mode.RESOURCE -> {
                val file = resources.json(source)
                parseJson(file.toString())
            }
        }
    }

    private fun parseJson(content: String): Equation {
        return Gson().fromJson(content, Equation::class.java)
    }
}