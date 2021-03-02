package services.dao

enum class Mode {
    RESOURCE,
    FILE
}

interface DAO<T> {
    fun getItem(source: String, mode: Mode): T

    fun saveItem(source: String, t: T)
}