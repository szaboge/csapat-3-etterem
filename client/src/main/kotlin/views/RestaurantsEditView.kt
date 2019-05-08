package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.table
import globals.ui.ElementFactory.td
import globals.ui.ElementFactory.textfield
import globals.ui.ElementFactory.tr
import globals.ui.Routes
import models.database.FoodModel
import models.database.RestaurantModel
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLTableCellElement
import kotlin.browser.window
import kotlin.dom.addClass
import kotlin.dom.removeClass

class RestaurantsEditView: View() {
    override val routeType: Routes = Routes.RESTAURANTSEDIT

    lateinit var foodsDest: HTMLTableCellElement
    lateinit var restaurantsDest: HTMLTableCellElement
    private var restaurants: Array<RestaurantModel> = arrayOf()
    private var foods: Array<FoodModel> = arrayOf()
    private var restaurantsItems: MutableList<HTMLDivElement> = mutableListOf()

    private var actualRestaurant = -1

    override fun render() {
        with(root) {
            addClass("restedit-root")
            div {
                addClass("restedit-container")
                div {
                    table {
                        addClass("restedit-table")
                        tr {
                            td {

                            }
                            td {

                            }
                        }
                        tr {
                            td {
                                val item = textfield {
                                    placeholder = "Étterem neve"
                                    addClass("default-textfield")
                                }
                                button("hozzáadás") {
                                    addClass("default-button")
                                    addEventListener("click", {
                                        addRestaurant(item.value)
                                    })
                                }
                            }
                            td {
                                val name = textfield {
                                    placeholder = "Étel neve"
                                    addClass("default-textfield")
                                }
                                val price = textfield {
                                    placeholder = "Étel ára"
                                    addClass("default-textfield")
                                }
                                button("hozzáadás") {
                                    addClass("default-button")
                                    addEventListener("click", {
                                        addFood(name.value, price.value)
                                    })
                                }
                            }
                        }
                        tr {
                            restaurantsDest = td {

                            }
                            foodsDest = td {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun addRestaurant(name: String) {
        if (name == "") return
        val restaurant = object {
            val name = name
        }
        ApiService.addRestaurant(restaurant) {
            fetch()
        }
    }

    private fun addFood(name: String, price: String) {
        if (name == "" || price == "" || actualRestaurant == -1) return
        val food = object {
            val restaurantID = actualRestaurant
            val name = name
            val price = price
        }
        ApiService.addFood(food) {
            generateFoods(actualRestaurant)
        }
    }
    fun deleteFood(foodID: Int) {
        val food = object {
            val foodID = foodID
        }
        ApiService.removeFood(food) {
            generateFoods(actualRestaurant)
        }
    }

    fun deleteRestaurant(restaurantID: Int) {
        if (restaurantID == actualRestaurant) {
            foodsDest.innerHTML = ""
            actualRestaurant = -1
        }
        val restaurant = object {
            val restaurantID = restaurantID
        }
        ApiService.removeRestaurant(restaurant) {
            fetch()
        }
    }

    fun generateUI() {
        restaurantsDest.innerHTML = ""
        with(restaurantsDest) {
            restaurants.forEach {
                var item = div("restedit-restaurant-item") {
                    label(it.name)
                    icon("close") {
                        addClass("rest-icon-button to-basket")
                        addEventListener("click", { event ->
                            if (!window.confirm("Biztos hogy kitörlöd?")) return@addEventListener
                            deleteRestaurant(it.restaurantID)
                            event.stopPropagation()
                        })
                    }
                    if (actualRestaurant == it.restaurantID) {
                        addClass("restedit-restaurant-item-highlight")
                    }
                    addEventListener("click", { _ ->
                        println("ui")
                        restaurantsItems.forEach {
                            it.removeClass("restedit-restaurant-item-highlight")
                        }
                        addClass("restedit-restaurant-item-highlight")
                        actualRestaurant = it.restaurantID
                        generateFoods(it.restaurantID)
                    })
                }
                restaurantsItems.add(item)
            }
        }
    }

    fun generateFoods(restID: Int) {
        ApiService.getFoods(restID) {
            foods = it
            foodsDest.innerHTML = ""

            foods.forEach {food ->
                with(foodsDest) {
                    div("restedit-food-item") {
                        label(food.name)
                        div {
                            addClass("restedit-end")
                            label("${food.price} Ft")
                            icon("close") {
                                addClass("rest-icon-button to-basket")
                                addEventListener("click", {
                                    if (!window.confirm("Biztos hogy kitörlöd?")) return@addEventListener
                                    deleteFood(food.foodID)
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    fun fetch() {
        ApiService.getRestaurants {
            restaurants = it
            generateUI()
        }
    }

    override fun onShow() {
        fetch()
    }
}