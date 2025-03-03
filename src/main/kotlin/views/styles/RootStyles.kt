package views.styles

import tornadofx.Stylesheet
import tornadofx.px

class RootStyles : Stylesheet() {
    companion object {
        const val PREF_HEIGHT = 720
        const val PREF_WIDTH = 1080
    }

    init {
        root {
            prefHeight = PREF_HEIGHT.px
            prefWidth = PREF_WIDTH.px
        }
    }
}