package views

import abstracts.View
import globals.ElementFactory.Button
import globals.Routes

class MainView: View() {
    override val routeType = Routes.MAIN

    override fun render(): View {
        val button = Button("Éttermek letöltése")
        root.appendChild(button)
        return this
    }
}