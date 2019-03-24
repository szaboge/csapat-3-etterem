package abstracts

import globals.Presenter
import globals.Routes
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

abstract class View() {
    abstract val routeType: Routes
    abstract fun render(): View

    var root: HTMLElement = document.createElement("div") as HTMLDivElement
    fun show() = Presenter.present(root)
    fun add(item: HTMLElement) = root.appendChild(item)
    fun build(): View {
        root = document.createElement("div") as HTMLDivElement
        render()
        return this
    }
}