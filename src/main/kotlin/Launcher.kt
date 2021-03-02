import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App
import tornadofx.importStylesheet
import views.RootView
import views.optionView.styles.OptionStyles
import views.styles.RootStyles

class Launcher : App(RootView::class, RootStyles::class) {
    init {
        importStylesheet<OptionStyles>()
    }

    override fun start(stage: Stage) {
        stage.icons.add(Image("yarki.png"))
        super.start(stage)
    }
}