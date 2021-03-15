package views.styles

import tornadofx.Stylesheet
import tornadofx.px

class RootStyles : Stylesheet() {
    companion object {
        const val PREF_HEIGHT = 1080
        const val PREF_WIDTH = 1920
    }

    init {
        root {
            prefHeight = PREF_HEIGHT.px
            prefWidth = PREF_WIDTH.px
        }
    }
}