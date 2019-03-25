package views

import abstracts.View
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.Routes

class MainView: View() {
    override val routeType = Routes.MAIN

    override fun onShow() {

    }

    override fun render(): View {
        root.div {
            label {
                textContent = "HelloMainView"
            }
        }
        return this
    }
}
