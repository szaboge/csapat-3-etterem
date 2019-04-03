package views

import abstracts.View
import globals.UserChangeListener
import globals.UserService
import globals.ui.ElementFactory
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.RouterService
import globals.ui.Routes
import models.communication.UserByTokenModel
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLLabelElement
import kotlin.dom.addClass

class MenuView: View(), UserChangeListener{
    override fun onUserChange(newValue: UserByTokenModel) {
        userRole.textContent = newValue.role
        if (newValue.role == "GUEST") return
        destButton.innerHTML = ""
        destButton.button("LOGOUT") {
            addClass("default-button")
            addEventListener("click", {
                UserService.logout()
                destButton.innerHTML = ""
                destButton.button("LOGIN") {
                    addClass("default-button")
                    addEventListener("click", {
                        RouterService.navigate(Routes.LOGIN)
                    })
                }
                userRole.textContent = ""
            })
        }
    }

    override val routeType: Routes = Routes.MENU

    lateinit var userControl: HTMLElement
    lateinit var userRole: HTMLLabelElement
    lateinit var destButton: HTMLDivElement
    override fun onShow() {

    }

    override fun render() {
        UserService.subscribe(this)
        root.addClass("menu-content")

        root.div {

        }
        root.div {
            button("Home") {
                addClass("menu-item")
                addEventListener("click", {
                    RouterService.navigate(Routes.HOME)
                })
            }
            button("Restaurants") {
                addClass("menu-item")
                addEventListener("click", {
                    RouterService.navigate(Routes.RESTAURANTS)
                })
            }
            button("Orders") {
                addClass("menu-item")
                addEventListener("click", {
                    RouterService.navigate(Routes.ORDERS)
                })
            }
        }
        userControl = root.div {
            addClass("user-control")
            userRole = label("") {
                addClass("role")
            }
            destButton = div {
                button("LOGIN") {
                    addClass("default-button")
                    addEventListener("click", {
                        RouterService.navigate(Routes.LOGIN)
                    })
                }
            }
        }
    }
}