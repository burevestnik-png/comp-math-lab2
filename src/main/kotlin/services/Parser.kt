package services

class Parser {
    companion object {
        fun isCos(token: String): Boolean = token.contains("cos", ignoreCase = true)
        fun isSin(token: String): Boolean = token.contains("sin", ignoreCase = true)
        fun isExp(token: String): Boolean = token.contains("e", ignoreCase = true)
        fun getInt(token: String) = "\\d".toRegex().find(token)!!.value.toDouble()

        fun getInts(token: String): MutableList<Int> {
            val factors = mutableListOf<Int>()
            "\\d".toRegex().findAll(token).iterator().forEach { result: MatchResult ->
                factors.add(result.value.toInt())
            }
            return factors
        }
    }
}