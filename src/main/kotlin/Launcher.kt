import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App
import views.RootView

class Launcher: App(RootView::class) {
    override fun start(stage: Stage) {
        stage.icons.add(Image("yarki.png"))
        super.start(stage)
    }
}