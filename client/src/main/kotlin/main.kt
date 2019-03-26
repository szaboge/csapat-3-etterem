import globals.ui.RouterService
import globals.ui.Routes
import org.w3c.dom.HTMLDivElement
import views.MenuView
import kotlin.browser.document

fun main() {
    val menu = document.getElementById("menu") as HTMLDivElement
    menu.appendChild(MenuView().build().root)
    RouterService.navigate(Routes.HOME)
}