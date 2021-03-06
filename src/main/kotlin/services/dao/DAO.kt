package services.dao

enum class Mode {
    RESOURCE,
    FILE
}

interface DAO<T> {
    fun getAll(clazz: Class<T>, mode: Mode): List<T>
    fun getItem(clazz: Class<T>, mode: Mode = Mode.FILE, source: String = ""): T
    fun saveItem(t: T)
}