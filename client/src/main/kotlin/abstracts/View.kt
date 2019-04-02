package abstracts

import globals.ui.Presenter
import globals.ui.Routes
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLLabelElement
import kotlin.browser.document

abstract class View() {
    abstract val routeType: Routes
    abstract fun render()
    abstract fun onShow()

    var root: HTMLElement = document.createElement("div") as HTMLDivElement
    fun add(item: HTMLElement) = root.appendChild(item)

    fun show() {
        build()
        Presenter.present(root)
        onShow()
    }

    fun build(): View {
        root = document.createElement("div") as HTMLDivElement
        render()
        return this
    }


}