package views

import abstracts.View
import globals.UserChangeListener
import globals.UserService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.RouterService
import globals.ui.Routes
import models.communication.UserByTokenModel
import org.w3c.dom.HTMLElement
import kotlin.dom.addClass

class MenuView: View(), UserChangeListener{
    override fun onUserChange(newValue: UserByTokenModel) {
        println(newValue.role)
    }

    override val routeType: Routes = Routes.MENU

    lateinit var userControl: HTMLElement
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
        root.div {
            userControl = div {
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