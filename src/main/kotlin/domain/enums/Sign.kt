package domain.enums

enum class Sign {
    PLUS {
        override fun toString() = "+"
    },
    MINUS {
        override fun toString() = "-"
    };

    companion object {
        fun identifySign(content: String) = when (content) {
            "-" -> MINUS
            "+" -> PLUS
            else -> throw RuntimeException("Internal error")
        }

        fun isSign(content: String) = content == "-" || content == "+"
    }
}