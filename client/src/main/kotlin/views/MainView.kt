package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.Routes
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.dom.addClass

class MainView: View() {
    override val routeType = Routes.MAIN

    override fun onShow() {
    }

    override fun render(): View {
        root.div {
            label("Main")
        }
        return this
    }
}
