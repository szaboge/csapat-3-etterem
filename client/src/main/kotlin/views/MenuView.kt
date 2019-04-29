package views

import abstracts.View
import components.DropdownMenu
import globals.UserChangeListener
import globals.UserService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.span
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import models.communication.UserByTokenModel
import org.w3c.dom.HTMLElement
import kotlin.dom.addClass

class MenuView: View(), UserChangeListener {
    override fun onUserChange(newValue: UserByTokenModel) {
        userControl.innerHTML = ""
        if (newValue.role == "GUEST" || newValue.role == "UNAUTHORIZED") {
            with(userControl) {
                button(Lang.getText("login")) {
                    addClass("default-button")
                    addEventListener("click", {
                        RouterService.navigate(Routes.LOGIN)
                    })
                }
            }
            return
        }
        DropdownMenu.generate(newValue.role)
        with(userControl) {
            div {
                span(newValue.name) {
                    addClass("user-item")
                }
                icon("chevron-down")
                addEventListener("click", {
                    DropdownMenu.toggle()
                })
            }
        }
    }

    override val routeType: Routes = Routes.MENU

    lateinit var userControl: HTMLElement
    override fun onShow() {

    }

    override fun render() {
        UserService.subscribe(this)
        with(root) {
            addClass("menu-content")
            div()
            div {
                button(Lang.getText("home")) {
                    addClass("menu-item")
                    addEventListener("click", {
                        RouterService.navigate(Routes.HOME)
                    })
                }
                button(Lang.getText("restaurants")) {
                    addClass("menu-item")
                    addEventListener("click", {
                        RouterService.navigate(Routes.RESTAURANTS)
                    })
                }
            }
            userControl = div {
                addClass("user-control")
                button(Lang.getText("login")) {
                    addClass("default-button")
                    addEventListener("click", {
                        RouterService.navigate(Routes.LOGIN)
                    })
                }
            }
        }

    }
}