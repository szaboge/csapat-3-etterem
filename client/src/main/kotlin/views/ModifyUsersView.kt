package views

import ApiService
import abstracts.View
import globals.Enums
import globals.ui.ElementFactory
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.option
import globals.ui.ElementFactory.select
import globals.ui.Routes
import models.database.RestaurantModel
import models.database.UserModel
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSelectElement
import kotlin.browser.window
import kotlin.dom.addClass

class ModifyUsersView : View() {
    override val routeType: Routes = Routes.MODIFYUSERS
    lateinit var destination: HTMLDivElement

    var restaurants: Array<RestaurantModel> = arrayOf()
    var users: Array<UserModel> = arrayOf()
    var ended: Int = 0

    override fun render() {
        with(root) {
            addClass("users-root")
            div("users-container") {
                label("Felhasználók") {
                    addClass("title")
                }
                destination = div("users-destination") {

                }
            }
        }
    }

    override fun onShow() {
        fetchAll()
    }

    private fun generateUI() {
        if (ended < 2) return
        destination.innerHTML = ""

        with(destination) {
            div("users-item-title") {
                label("ADMINOK")
            }
            users.filter { user -> user.role == "ADMIN" }.forEach {
                appendChild(createItem(it))
            }
            div("users-item-title") {
                label("KONYHÁK")
            }
            users.filter { user -> user.role == "KITCHEN" }.forEach {
                appendChild(createItem(it))
            }
            div("users-item-title") {
                label("FUTÁROK")
            }
            users.filter { user -> user.role == "RIDER" }.forEach {
                appendChild(createItem(it))
            }
            div("users-item-title") {
                label("FELHASZNÁLÓK")
            }
            users.filter { user -> user.role == "USER" }.forEach {
                appendChild(createItem(it))
            }
            div("users-item-title") {
                label("VENDÉGEK")
            }
            users.filter { user -> user.role == "GUEST" }.forEach {
                appendChild(createItem(it))
            }
        }
    }

    fun createItem(user: UserModel): HTMLDivElement {
        return with(div()) {
            addClass("users-item-container")
            div {
                label(user.name + " - " + user.email)
            }
            div {
                appendChild(createRestaurantDropDown(user))
                appendChild(createRolesDropDown(user))
                icon("close") {
                    addClass("icon-button to-basket")
                    addEventListener("click", {
                        if (!window.confirm("Biztos hogy kitörlöd?")) return@addEventListener
                        ApiService.deleteUser(object {
                            val userID = user.userID
                        }) {
                            fetchAll()
                        }
                    })
                }
            }
            this
        }
    }

    fun createRestaurantDropDown(user: UserModel): HTMLSelectElement {
        var index = restaurants.indexOf(restaurants.find { restaurant -> restaurant.restaurantID == user.restaurantID })
        if (index == -1) index = 0 else index += 1
        return with(select()) {
            addClass("default-select")
            option("Nincs", "0")
            restaurants.forEach { restaurant ->
                option(restaurant.name, restaurant.restaurantID.toString())
            }
            selectedIndex = index
            addEventListener("change", {
                ApiService.modifyUser(object {
                    val userID = user.userID
                    val restaurantID = this@with.value
                    val role: String = user.role
                }) {
                    fetchAll()
                }
            })
            this
        }
    }

    fun createRolesDropDown(user: UserModel): HTMLSelectElement {
        var index = Enums.Roles.values().indexOf(Enums.Roles.values().find { role -> role.name == user.role })
        return with(select()) {
            addClass("default-select")
            Enums.Roles.values().forEach {
                option(it.s, it.name)
            }
            selectedIndex = index
            addEventListener("change", {
                ApiService.modifyUser(object {
                    val userID = user.userID
                    val restaurantID = user.restaurantID
                    val role: String = this@with.value
                }) {
                    fetchAll()
                }
            })
            this
        }
    }

    fun fetchAll() {
        ended = 0
        ApiService.getUsers {
            users = it
            ended++
            generateUI()
        }
        ApiService.getRestaurants {
            restaurants = it
            ended++
            generateUI()
        }
    }
}