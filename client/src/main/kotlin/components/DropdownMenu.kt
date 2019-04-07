package components

import globals.UserService
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.RouterService
import globals.ui.Routes
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
            else -> defaultMenu()
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
        val div = div()
        with(div) {
            icon("logout")
            span("LOGOUT")
            addClass("dropdown-item")
            addEventListener("click", {
                UserService.logout()
                RouterService.navigate(Routes.HOME)
                hide()
            })
        }
        return div
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