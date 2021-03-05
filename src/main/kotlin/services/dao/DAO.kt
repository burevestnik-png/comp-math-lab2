package services.dao

enum class Mode {
    RESOURCE,
    FILE
}

interface DAO<T> {
    fun getAll(mode: Mode): Array<T>
    fun getItem(mode: Mode = Mode.FILE, source: String = ""): T?
    fun saveItem(source: String, t: T)
}