package components

import globals.UserService
import globals.ui.ElementFactory
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import org.w3c.dom.HTMLDListElement
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.removeClass

object DropdownMenu {
    var open = false
    var dropdownContainer: HTMLDivElement = document.body!!.div {
        addClass("dropdown-container hide-dropdown")
    }

    fun generate(role: String) {
        dropdownContainer.innerHTML = ""
        when(role) {
            "ADMIN" -> adminMenu()
            "USER"  -> userMenu()
            "RIDER" -> riderMenu()
            "KITCHEN" -> kitchenMenu()
            else -> defaultMenu()
        }
    }

    private fun kitchenMenu() {
        with(dropdownContainer) {
            addClass("dropdown-wrapper")
            div {
                addClass("dropdown-collection")
                appendChild(logout())
            }
        }
    }

    private fun riderMenu() {
        with(dropdownContainer) {
            addClass("dropdown-wrapper")
            div {
                addClass("dropdown-collection")
                appendChild(createMenuItem("package-variant-closed", "AKTÍV RENDELÉSEK",  Routes.RIDERORDERS))
                appendChild(logout())
            }
        }
    }

    fun adminMenu() {
        with(dropdownContainer) {
            addClass("dropdown-wrapper")
            div {
                addClass("dropdown-collection")
                appendChild(logout())
            }
        }
    }

    fun userMenu() {
        with(dropdownContainer) {
            addClass("dropdown-wrapper")
            div {
                addClass("dropdown-collection")
                appendChild(createMenuItem("package-variant-closed", Lang.getText("dropdown-myorders"), Routes.MYORDERS))
                appendChild(logout())
            }
        }
    }

    fun createMenuItem(icon:String, text: String, route: Routes): HTMLDivElement {
        return with(div()) {
            icon(icon)
            span(text)
            addClass("dropdown-item")
            addEventListener("click", {
                RouterService.navigate(route)
                hide()
            })
            this
        }
    }

    fun defaultMenu() {
        with(dropdownContainer) {
            addClass("dropdown-wrapper")
            div {
                addClass("dropdown-collection")
                appendChild(logout())
            }
        }
    }


    fun logout(): HTMLDivElement {
        return with(div()) {
            icon("logout")
            span(Lang.getText("logout").toUpperCase())
            addClass("dropdown-item")
            addEventListener("click", {
                UserService.logout()
                RouterService.navigate(Routes.HOME)
                hide()
            })
            this
        }
    }

    fun show() {
        dropdownContainer.removeClass("hide-dropdown")
        open = true
    }

    fun hide() {
        dropdownContainer.addClass("hide-dropdown")
        open = false
    }

    fun toggle() {
        if (open) hide() else show()
    }
}