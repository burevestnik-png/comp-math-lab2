package domain.enums

enum class Sign {
    PLUS {
        override fun toString(): String {
            return "+"
        }
    },
    MINUS {
        override fun toString(): String {
            return "-"
        }
    };

    companion object {
        fun identifySign(content: String): Sign {
            return when (content) {
                "-" -> MINUS
                "+" -> PLUS
                else -> throw RuntimeException("Internal error")
            }
        }

        fun isSign(content: String): Boolean {
            return content == "-" || content == "+"
        }
    }
}