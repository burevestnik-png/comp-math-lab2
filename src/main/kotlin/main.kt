import tornadofx.*

class MyView: View() {
    override val root = vbox {
        button("Press me")
        label("Waiting")
    }
}

class LabApp: App(MyView::class)

fun main(args: Array<String>) {
    launch<LabApp>(args)
}