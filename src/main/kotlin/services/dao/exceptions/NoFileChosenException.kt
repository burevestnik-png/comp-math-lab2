package services.dao.exceptions

class NoFileChosenException(message: String?) : Exception(message) {
    override fun toString() = "No file chosen!"
}
